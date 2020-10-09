package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.RdfContext;
import cz.cvut.kbss.sformsmanager.persistence.connection.RepositoryConnectionEntityManagerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ContextRepository {

    private final RepositoryConnectionEntityManagerProvider entityManagerProvider;

    public List<RdfContext> findAll(String repositoryName) {
        EntityManager emf = entityManagerProvider.getEntityManagerFactory(repositoryName);
        return emf.getContexts().stream().map(RdfContext::new).limit(10).collect(Collectors.toList());
    }
}
