package cz.cvut.kbss.sformsmanager.persistence.base;

import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.sformsmanager.config.provider.ConnectedRepositoryConfigProvider;
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
public class ConnectedRepositoryEntityManagerProvider {

    private final ConnectedRepositoryConfigProvider connectedRepositoryConfigProvider;

    private final ConcurrentMap<String, EntityManager> activeEntityManagerMap = new ConcurrentHashMap();

    public EntityManager getEntityManagerFactory(String connectionName) {
        if (!connectedRepositoryConfigProvider.getConnectionMap().containsKey(connectionName)) {
            throw new RuntimeException(String.format("Repository connection with connectionName '%s' does not exist.", connectionName));
        }
        if (activeEntityManagerMap.containsKey(connectionName) && activeEntityManagerMap.get(connectionName).isOpen()) {
            return activeEntityManagerMap.get(connectionName);
        }

        ConnectedRepositoryConfigProvider.ConnectedRepositoryConfig connection = connectedRepositoryConfigProvider.getConnectionMap().get(connectionName);

        final Map<String, String> properties = new HashMap<>();
        properties.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, connection.getFormGenRepositoryUrl());
        properties.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS, "cz.cvut.kbss.ontodriver.sesame.SesameDataSource");
        properties.put(JOPAPersistenceProperties.SCAN_PACKAGE, "cz.cvut.kbss.sformsmanager.model.persisted");
        properties.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());

        // TODO: is there a different way to read the repository with only read access?

        log.info("Creating repository connection with connectionName {}.", connectionName);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sformsmanager", properties);
        EntityManager em = emf.createEntityManager();
        activeEntityManagerMap.put(connectionName, em);
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