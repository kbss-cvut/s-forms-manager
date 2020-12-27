package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.Context;
import cz.cvut.kbss.sformsmanager.persistence.base.ConnectionEntityManagerProvider;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContextRepository {

    private final ConnectionEntityManagerProvider entityManagerProvider;

    public ContextRepository(ConnectionEntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public List<Context> findAll(String connectionName) {
        EntityManager emf = entityManagerProvider.getEntityManagerFactory(connectionName);
        return emf.getContexts().stream().map(URI::toString).map(Context::new).collect(Collectors.toList());
    }


    public List<Context> findPaginated(String connectionName, int offset, int limit) {
        EntityManager emf = entityManagerProvider.getEntityManagerFactory(connectionName);
        return emf.getContexts().stream().skip(offset).limit(limit).map(URI::toString).map(Context::new).collect(Collectors.toList());
    }

    public int count(String connectionName) {
        EntityManager emf = entityManagerProvider.getEntityManagerFactory(connectionName);
        return emf.getContexts().size();
    }
}
