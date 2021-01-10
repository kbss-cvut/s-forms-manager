package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;
import cz.cvut.kbss.sformsmanager.persistence.dao.BaseDAO;
import org.slf4j.Logger;
import org.springframework.lang.NonNull;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Base implementation of the generic DAO.
 */
public abstract class LocalEntityBaseDAO<T extends LocalEntity> extends BaseDAO<T> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LocalEntityBaseDAO.class);
    protected final EntityManager em;

    protected LocalEntityBaseDAO(EntityManager em, Class<T> type) {
        super(type);
        this.em = em;
    }

    public List<T> findAll() {
        return super.findAll(em);
    }

    public Optional<T> find(@NonNull URI id) {
        return super.find(em, id);
    }

    public Optional<T> getReference(@NonNull URI id) {
        return super.getReference(em, id);
    }

    public void persist(@NonNull T entity) {
        super.persist(em, entity);
    }

    public void persist(@NonNull Collection<T> entities) {
        super.persist(em, entities);
    }

    public T update(@NonNull T entity) {
        return super.update(em, entity);
    }

    public void remove(@NonNull T entity) {
        super.remove(em, entity);
    }

    public void remove(@NonNull URI id) {
        super.remove(em, id);
    }

    public boolean exists(@NonNull URI id) {
        return super.exists(em, id);
    }

    public int count() {
        return super.count(em);
    }

    public Optional<T> findByKey(@NonNull String key) {
        return super.findByKey(em, key);
    }

    public void persistIfNotExists(@NonNull T entity) {
        if (findByKey(em, entity.getKey()).isPresent()) {
            throw new PersistenceException("The entity with " + entity.getKey() + " already exist.");
        }
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not persist entity.", e);
        }
    }


}