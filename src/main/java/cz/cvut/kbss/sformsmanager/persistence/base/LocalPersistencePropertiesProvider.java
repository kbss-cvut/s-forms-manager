package cz.cvut.kbss.sformsmanager.persistence.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class LocalPersistencePropertiesProvider {

    @Value("${driver}")
    private String driver;

    @Value("${RDF4J_REPOSITORY_URL}")
    private String repositoryUrl;

    public LocalPersistencePropertiesProvider() {
    }

    public String getDriver() {
        return driver;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }
}
