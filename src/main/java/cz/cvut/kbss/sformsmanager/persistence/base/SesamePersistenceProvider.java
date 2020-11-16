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
import cz.cvut.kbss.sformsmanager.config.provider.PropertiesProvider;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenVersionTag;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.config.RepositoryConfigException;
import org.eclipse.rdf4j.repository.manager.RepositoryProvider;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URI;

@Configuration
public class SesamePersistenceProvider {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SesamePersistenceProvider.class);
    private final PropertiesProvider propertiesProvider;
    private final EntityManagerFactory emf;

    private Repository repository;

    public SesamePersistenceProvider(PropertiesProvider propertiesProvider, EntityManagerFactory emf) {
        this.propertiesProvider = propertiesProvider;
        this.emf = emf;
    }

    @Bean
    public Repository repository() {
        return repository;
    }

    @PostConstruct
    private void initializeStorage() {
        forceRepoInitialization();
        final String repoUrl = propertiesProvider.getRepositoryUrl();
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
            em.find(FormGenMetadata.class, URI.create("http://unknown"));
            em.find(FormGenVersionTag.class, URI.create("http://unknown"));
        } finally {
            em.close();
        }
    }
}
