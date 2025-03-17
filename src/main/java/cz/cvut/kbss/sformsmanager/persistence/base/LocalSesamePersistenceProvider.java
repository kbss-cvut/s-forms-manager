/**
 * Copyright (C) 2019 Czech Technical University in Prague
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.sformsmanager.persistence.base;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.sformsmanager.model.persisted.local.Record;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.config.RepositoryConfigException;
import org.eclipse.rdf4j.repository.manager.RepositoryProvider;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.net.URI;

@Configuration
public class LocalSesamePersistenceProvider {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LocalSesamePersistenceProvider.class);
    private final LocalPersistencePropertiesProvider localPersistencePropertiesProvider;
    private final EntityManagerFactory emf;

    private Repository repository;

    public LocalSesamePersistenceProvider(LocalPersistencePropertiesProvider localPersistencePropertiesProvider, EntityManagerFactory emf) {
        this.localPersistencePropertiesProvider = localPersistencePropertiesProvider;
        this.emf = emf;
    }

    @Bean
    public Repository repository() {
        return repository;
    }

    @PostConstruct
    private void initializeStorage() {
        forceRepoInitialization();
        final String repoUrl = localPersistencePropertiesProvider.getRepositoryUrl();
        try {
            this.repository = RepositoryProvider.getRepository(repoUrl);
            assert repository.isInitialized();
        } catch (RepositoryException | RepositoryConfigException e) {
            log.error("Unable to connect to Sesame repository at " + repoUrl, e);
        }
    }

    private void forceRepoInitialization() {
        final EntityManager em = emf.createEntityManager();
        try {
            // The URI doesn't matter, we just need to trigger repository connection initialization
            em.find(Record.class, URI.create("http://unknown"));
        } finally {
            em.close();
        }
    }
}
