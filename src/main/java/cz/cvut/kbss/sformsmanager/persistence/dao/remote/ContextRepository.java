package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.remote.Context;
import cz.cvut.kbss.sformsmanager.persistence.base.RemoteProjectEntityManagerProvider;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContextRepository {

    private final RemoteProjectEntityManagerProvider entityManagerProvider;

    public ContextRepository(RemoteProjectEntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public List<Context> findAll(String projectName) {
        EntityManager em = entityManagerProvider.getEntityManager(projectName);
        return em.getContexts().stream().map(URI::toString).map(Context::new).collect(Collectors.toList());
    }

    public List<Context> findPaginated(String projectName, int offset, int limit) {
        EntityManager em = entityManagerProvider.getEntityManager(projectName);
        return em.getContexts().stream().skip(offset).limit(limit).map(URI::toString).map(Context::new).collect(Collectors.toList());
    }

    public int count(String projectName) {
        EntityManager em = entityManagerProvider.getEntityManager(projectName);
        return em.getContexts().size();
    }
}
