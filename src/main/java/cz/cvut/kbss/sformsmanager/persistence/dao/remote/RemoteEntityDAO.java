package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.persistence.base.RemoteProjectEntityManagerProvider;
import org.slf4j.Logger;

import java.io.IOException;

public abstract class RemoteEntityDAO<T> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RemoteEntityDAO.class);

    protected RemoteProjectEntityManagerProvider entityManagerProvider;
    protected final Class<T> type;

    protected RemoteEntityDAO(RemoteProjectEntityManagerProvider entityManagerProvider, Class<T> type) {
        this.type = type;
        this.entityManagerProvider = entityManagerProvider;
    }

    public EntityManager getEntityManagerByConnection(String connectionName) {
        return entityManagerProvider.getEntityManagerFactory(connectionName);
    }

    public T executeOnEntityManager(String connectionName, EntityManagerInvoker emConsumer, String errorMessage) throws IOException {
        try {
            EntityManager em = getEntityManagerByConnection(connectionName);
            return (T) emConsumer.accept(em);
        } catch (NoResultException ne) {
            throw new NoResultException(errorMessage);
        }
    }

    interface EntityManagerInvoker<T> {
        Object accept(EntityManager entityManager) throws IOException;
    }
}