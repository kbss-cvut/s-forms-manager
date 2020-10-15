package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.RdfContext;
import cz.cvut.kbss.sformsmanager.persistence.base.ConnectedRepositoryEntityManagerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ContextRepository {

    private final ConnectedRepositoryEntityManagerProvider entityManagerProvider;

    public List<RdfContext> findAll(String connectionName) {
        EntityManager emf = entityManagerProvider.getEntityManagerFactory(connectionName);
        return emf.getContexts().stream().map(RdfContext::new).limit(100).collect(Collectors.toList());
    }
}
