package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
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
public abstract class BaseDAO<T> implements GenericDAO<T> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BaseDAO.class);

    protected final Class<T> type;
    protected final URI typeUri;

    protected BaseDAO(Class<T> type) {
        this.type = type;
        this.typeUri = URI.create(OWLUtils.getOwlClassForEntity(type));
    }

    @Override
    public List<T> findAll(EntityManager em) {
        try {
            return em.createNativeQuery("SELECT ?x WHERE { ?x a ?type . }", type)
                    .setParameter("type", typeUri)
                    .getResultList();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<T> find(EntityManager em, @NonNull URI id) {
        try {
            return Optional.ofNullable(em.find(type, id));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<T> getReference(EntityManager em, @NonNull URI id) {
        try {
            return Optional.ofNullable(em.getReference(type, id));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }

    @Override
    public void persist(EntityManager em, @NonNull T entity) {
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not persist entity.", e);
        }
    }

    @Override
    public void persist(EntityManager em, @NonNull Collection<T> entities) {
        if (entities.isEmpty()) {
            return;
        }
        entities.forEach(em::persist);
    }

    @Override
    public T update(EntityManager em, @NonNull T entity) {
        try {
            return em.merge(entity);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not update entity.", e);
        }
    }

    @Override
    public void remove(EntityManager em, @NonNull T entity) {
        try {
            em.remove(em.merge(entity));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not remove entity.", e);
        }
    }

    @Override
    public void remove(EntityManager em, @NonNull URI id) {
        find(em, id).ifPresent(em::remove);
    }

    @Override
    public boolean exists(EntityManager em, @NonNull URI id) {
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
    public int count(EntityManager em) {
        try {
            return (int) em.createNativeQuery(
                    "SELECT (count(?x) as ?object) WHERE { ?x a ?type }")
                    .setParameter("type", typeUri).getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'count' on " + typeUri.toString() + ".", e);
        }
    }

    public Optional<T> findByKey(EntityManager em, @NonNull String key) {
        try {
            return Optional.of(em.createNativeQuery("SELECT ?x WHERE { ?x ?hasKey ?key ;a ?type }", type)
                    .setParameter("hasKey", URI.create(Vocabulary.p_key))
                    .setParameter("key", key)
                    .setParameter("type", typeUri).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}