package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.HasUniqueKey;
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
public abstract class BaseDao<T extends HasUniqueKey> implements GenericDao<T> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BaseDao.class);
    protected final EntityManager em; // TODO: should we use entity manager factory instead?
    protected final Class<T> type;
    protected final URI typeUri;

    protected BaseDao(EntityManager em, Class<T> type) {
        this.em = em;
        this.type = type;
        this.typeUri = URI.create(OWLUtils.getOwlClassForEntity(type));
    }

    @Override
    public List<T> findAll() {
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
    public Optional<T> find(@NonNull URI id) {
        try {
            return Optional.ofNullable(em.find(type, id));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<T> findByKey(@NonNull String key) {
        try {
            return Optional.of(em.createNativeQuery("SELECT ?x WHERE { ?x ?hasKey ?key ;a ?type }", type)
                    .setParameter("hasKey", URI.create(Vocabulary.p_key))
                    .setParameter("key", key)
                    .setParameter("type", typeUri).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> getReference(@NonNull URI id) {
        try {
            return Optional.ofNullable(em.getReference(type, id));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }

    @Override
    public void persist(@NonNull T entity) {
        if (findByKey(entity.getKey()).isPresent()) {
            throw new PersistenceException("The entity with " + entity.getKey() + " already exist.");
        }
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not persist entity.", e);
        }
    }

    @Override
    public void persist(@NonNull Collection<T> entities) {
        if (entities.isEmpty()) {
            return;
        }
        entities.forEach(em::persist);
    }

    @Override
    public T update(@NonNull T entity) {
        try {
            return em.merge(entity);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not update entity.", e);
        }
    }

    @Override
    public void remove(@NonNull T entity) {
        try {
            em.remove(em.merge(entity));
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

    public int count() {
        try {
            return (int) em.createNativeQuery(
                    "SELECT (count(?p) as ?object) WHERE { ?x a ?type }")
                    .setParameter("type", typeUri).getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'count' on " + typeUri.toString() + ".", e);
        }
    }
}