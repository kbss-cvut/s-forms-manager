package cz.cvut.kbss.sformsmanager.persistence.connection;

import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.sformsmanager.config.provider.ConnectionProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class RepositoryConnectionEntityManagerProvider {

    private final ConnectionProvider connectionProvider;

    private final ConcurrentMap<String, EntityManager> activeEntityManagerMap = new ConcurrentHashMap();

    public EntityManager getEntityManagerFactory(String repositoryName) {
        if (!connectionProvider.getConnectionMap().containsKey(repositoryName)) {
            throw new RuntimeException(String.format("Repository connection with repositoryName '%s' does not exist.", repositoryName));
        }
        if (activeEntityManagerMap.containsKey(repositoryName) && activeEntityManagerMap.get(repositoryName).isOpen()) {
            return activeEntityManagerMap.get(repositoryName);
        }

        ConnectionProvider.RepositoryConnection connection = connectionProvider.getConnectionMap().get(repositoryName);

        final Map<String, String> properties = new HashMap<>();
        properties.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, connection.getFormGenRepositoryUrl());
        properties.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS, "cz.cvut.kbss.ontodriver.sesame.SesameDataSource");
        properties.put(JOPAPersistenceProperties.SCAN_PACKAGE, "cz.cvut.kbss.sformsmanager.model");
        properties.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());

        // TODO: is there a different way to read the repository with only read access?

        log.info("Creating repository connection with repositoryName {}.", repositoryName);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sformsmanager", properties);
        EntityManager em = emf.createEntityManager();
        activeEntityManagerMap.put(repositoryName, em);
        return em;
    }

    @PreDestroy
    private void close() {
        // TODO: do we also need to close the EntityManagerFactory?
        for (EntityManager e : activeEntityManagerMap.values()) {
            if (e.isOpen()) {
                e.close();
            }
        }
    }
}