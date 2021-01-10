package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.remote.Context;
import cz.cvut.kbss.sformsmanager.persistence.base.ConnectionEntityManagerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContextRepository {

    private final ConnectionEntityManagerProvider entityManagerProvider;

    @Autowired
    public ContextRepository(ConnectionEntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public List<Context> findAll(String connectionName) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        return em.getContexts().stream().map(URI::toString).map(Context::new).collect(Collectors.toList());
    }

    public List<Context> findPaginated(String connectionName, int offset, int limit) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        return em.getContexts().stream().skip(offset).limit(limit).map(URI::toString).map(Context::new).collect(Collectors.toList());
    }

    public int count(String connectionName) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
        return em.getContexts().size();
    }
}
