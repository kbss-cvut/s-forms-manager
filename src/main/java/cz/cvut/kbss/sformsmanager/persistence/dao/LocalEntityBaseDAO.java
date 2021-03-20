package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.descriptors.EntityDescriptor;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.custom.CustomQueryDAO;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;
import org.slf4j.Logger;
import org.springframework.lang.NonNull;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Base implementation of the generic DAO.
 */
public abstract class LocalEntityBaseDAO<T> implements GenericDAO<T> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LocalEntityBaseDAO.class);

    protected EntityManager em;
    protected final Class<T> type;
    protected final URI typeUri;

    protected LocalEntityBaseDAO(EntityManager em, Class<T> type) {
        this.em = em;
        this.type = type;
        this.typeUri = URI.create(OWLUtils.getOwlClassForEntity(type));
    }

    public static EntityDescriptor getDescriptorForProject(String projectDescriptorName) {
        return new EntityDescriptor(URI.create(Vocabulary.ProjectContext + "/" + projectDescriptorName), false);
    }

    public static URI getProjectContextURI(String projectDescriptorName) {
        return URI.create(Vocabulary.ProjectContext + "/" + projectDescriptorName);
    }


    @Override
    public List<T> findAll(String projectDescriptorName) {
        try {
            return em.createNativeQuery("SELECT ?x WHERE { ?x a ?type . }", type)
                    .setDescriptor(getDescriptorForProject(projectDescriptorName))
                    .setParameter("type", typeUri)
                    .getResultList();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<T> findAllWhere(String projectDescriptorName, String propertyName, Object value) {
        try {
            return em.createNativeQuery("SELECT ?x WHERE { ?x a ?type . ?x ?propertyName ?value . }", type)
                    .setDescriptor(getDescriptorForProject(projectDescriptorName))
                    .setParameter("propertyName", URI.create(propertyName))
                    .setParameter("value", value)
                    .setParameter("type", typeUri)
                    .getResultList();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<T> find(@NonNull URI id) {
        try {
            return Optional.ofNullable(em.find(type, id));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<T> findFirstWhere(String projectDescriptorName, @NonNull String propertyName, @NonNull Object value) {
        try {
            return Optional.of(em.createNativeQuery("SELECT ?x WHERE { ?x a ?type . ?x ?propertyName ?value . } LIMIT 1", type)
                    .setDescriptor(getDescriptorForProject(projectDescriptorName))
                    .setParameter("propertyName", URI.create(propertyName))
                    .setParameter("value", value)
                    .setParameter("type", typeUri)
                    .getSingleResult());
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public Optional<T> findByKey(String projectDescriptorName, @NonNull String key) {
        return findFirstWhere(projectDescriptorName, Vocabulary.p_key, key);
    }

    @Override
    public void persist(String projectDescriptorName, @NonNull T entity) {
        try {
            em.persist(entity, getDescriptorForProject(projectDescriptorName));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not persist entity.", e);
        }
    }

    @Override
    public void persist(String projectDescriptorName, @NonNull Collection<T> entities) {
        if (entities.isEmpty()) {
            return;
        }
        entities.forEach(entity -> {
            em.persist(entity, getDescriptorForProject(projectDescriptorName));
        });
    }

    @Override
    public T update(String projectDescriptorName, @NonNull T entity) {
        try {
            return em.merge(entity, getDescriptorForProject(projectDescriptorName));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not update entity.", e);
        }
    }

    @Override
    public void remove(String projectDescriptorName, @NonNull T entity) {
        try {
            em.remove(em.merge(entity, getDescriptorForProject(projectDescriptorName)));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not remove entity.", e);
        }
    }

    @Override
    public void remove(@NonNull URI id) {
        find(id).ifPresent(em::remove);
    }

    @Override
    public boolean exists(@NonNull URI id) {
        try {
            return em.createNativeQuery("ASK { ?x a ?type . }", Boolean.class)
                    .setParameter("x", id)
                    .setParameter("type", typeUri).getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'exists' on " + id.toString() + ".", e);
        }
    }

    @Override
    public int count(String projectDescriptorName) {
        try {
            return em.createNativeQuery("SELECT (count(?x) as ?object) WHERE { GRAPH ?contextUri  { ?x a ?type . } }", Integer.class)
                    .setParameter(CustomQueryDAO.CUSTOM_QUERY_CONTEXT_URI_PARAM, getProjectContextURI(projectDescriptorName))
                    .setParameter("type", typeUri).getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'count' on " + typeUri.toString() + ".", e);
        }
    }

    @Override
    public int countWhere(String projectDescriptorName, @NonNull String propertyName, @NonNull Object value) {
        try {
            return em.createNativeQuery("SELECT (count(?x) as ?object) WHERE { GRAPH ?contextUri { ?x a ?type . ?x ?propertyName ?value . } }", Integer.class)
                    .setParameter(CustomQueryDAO.CUSTOM_QUERY_CONTEXT_URI_PARAM, getProjectContextURI(projectDescriptorName))
                    .setParameter("propertyName", URI.create(propertyName))
                    .setParameter("value", value)
                    .setParameter("type", typeUri)
                    .getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'count' on " + typeUri.toString() + ".", e);
        }
    }
}