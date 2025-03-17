package cz.cvut.kbss.sformsmanager.persistence.base;

import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.sformsmanager.model.persisted.local.Project;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.ProjectDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class RemoteProjectEntityManagerProvider {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RemoteProjectEntityManagerProvider.class);
    private final ProjectDAO projectDAO;

    private final ConcurrentMap<String, EntityManager> activeEntityManagerMap = new ConcurrentHashMap();

    @Autowired
    public RemoteProjectEntityManagerProvider(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public EntityManager getEntityManagerFactory(String projectDescriptorName) {
        if (activeEntityManagerMap.containsKey(projectDescriptorName) && activeEntityManagerMap.get(projectDescriptorName).isOpen()) {
            return activeEntityManagerMap.get(projectDescriptorName);
        }

        Project project = projectDAO.findByKey(projectDescriptorName, projectDescriptorName).orElseThrow(
                () -> new RuntimeException(String.format("Project '%s' does not exist.", projectDescriptorName)));

        final Map<String, String> properties = new HashMap<>();
        properties.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, project.getFormGenRepositoryUrl());
        properties.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS, "cz.cvut.kbss.ontodriver.rdf4j.Rdf4jDataSource");
        properties.put(JOPAPersistenceProperties.SCAN_PACKAGE, "cz.cvut.kbss.sformsmanager.model.persisted");
        properties.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());

        log.info("Creating project connection with name {} to repository at {}.", projectDescriptorName, project.getFormGenRepositoryUrl());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sformsmanager", properties);
        EntityManager em = emf.createEntityManager();
        activeEntityManagerMap.put(projectDescriptorName, em);
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