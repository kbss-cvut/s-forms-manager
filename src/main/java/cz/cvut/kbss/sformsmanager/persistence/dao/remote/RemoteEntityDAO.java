package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.persistence.base.ConnectionEntityManagerProvider;
import org.slf4j.Logger;

public abstract class RemoteEntityDAO<T> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RemoteEntityDAO.class);

    protected ConnectionEntityManagerProvider entityManagerProvider;
    protected final Class<T> type;

    protected RemoteEntityDAO(ConnectionEntityManagerProvider entityManagerProvider, Class<T> type) {
        this.type = type;
        this.entityManagerProvider = entityManagerProvider;
    }

    public EntityManager getEntityManagerByConnection(String connectionName) {
        return entityManagerProvider.getEntityManagerFactory(connectionName);
    }
}