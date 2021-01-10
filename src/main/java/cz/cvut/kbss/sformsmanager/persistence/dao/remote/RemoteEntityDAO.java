package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.persistence.base.ConnectionEntityManagerProvider;
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
public abstract class RemoteEntityDAO<T> extends BaseDAO<T> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RemoteEntityDAO.class);
    private ConnectionEntityManagerProvider entityManagerProvider;

    protected RemoteEntityDAO(ConnectionEntityManagerProvider entityManagerProvider, Class<T> type) {
        super(type);
        this.entityManagerProvider = entityManagerProvider;
    }

    // TODO: close entity manager after finishing

    public List<T> findAll(String connectionName) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        return super.findAll(em);
    }

    public Optional<T> find(String connectionName, @NonNull URI id) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        return super.find(em, id);
    }

    public Optional<T> getReference(String connectionName, @NonNull URI id) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        return super.getReference(em, id);
    }

    public void persist(String connectionName, @NonNull T entity) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        super.persist(em, entity);
    }

    public void persist(String connectionName, @NonNull Collection<T> entities) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        super.persist(em, entities);
    }

    public T update(String connectionName, @NonNull T entity) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        return super.update(em, entity);
    }

    public void remove(String connectionName, @NonNull T entity) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        super.remove(em, entity);
    }

    public void remove(String connectionName, @NonNull URI id) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        super.remove(em, id);
    }

    public boolean exists(String connectionName, @NonNull URI id) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        return super.exists(em, id);
    }

    public int count(String connectionName) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        return super.count(em);
    }
}