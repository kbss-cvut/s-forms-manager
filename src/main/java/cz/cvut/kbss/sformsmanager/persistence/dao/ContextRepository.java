package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.RdfContext;
import cz.cvut.kbss.sformsmanager.persistence.base.ConnectionEntityManagerProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContextRepository {

    private final ConnectionEntityManagerProvider entityManagerProvider;

    public ContextRepository(ConnectionEntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public List<RdfContext> findAll(String connectionName) {
        EntityManager emf = entityManagerProvider.getEntityManagerFactory(connectionName);
        return emf.getContexts().stream().map(RdfContext::new).limit(10).collect(Collectors.toList());
    }
}
