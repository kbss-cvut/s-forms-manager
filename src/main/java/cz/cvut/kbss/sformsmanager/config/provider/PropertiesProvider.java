package cz.cvut.kbss.sformsmanager.config.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesProvider {

    @Value("${driver}")
    private String driver;

    @Value("${repositoryUrl}")
    private String repositoryUrl;

    @Value("${default.connection.formGenRepositoryUrl}")
    private String defaultRepositoryUrl;

    @Value("${default.connection.formGenServiceUrl}")
    private String defaultServiceUrl;

    @Value("${default.connection.appRepositoryUrl}")
    private String defaultAppRepositoryUrl;


    public PropertiesProvider() {
    }

    public String getDriver() {
        return this.driver;
    }

    public String getRepositoryUrl() {
        return this.repositoryUrl;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getDefaultRepositoryUrl() {
        return defaultRepositoryUrl;
    }

    public void setDefaultRepositoryUrl(String defaultRepositoryUrl) {
        this.defaultRepositoryUrl = defaultRepositoryUrl;
    }

    public String getDefaultServiceUrl() {
        return defaultServiceUrl;
    }

    public void setDefaultServiceUrl(String defaultServiceUrl) {
        this.defaultServiceUrl = defaultServiceUrl;
    }

    public String getDefaultAppRepositoryUrl() {
        return defaultAppRepositoryUrl;
    }

    public void setDefaultAppRepositoryUrl(String defaultAppRepositoryUrl) {
        this.defaultAppRepositoryUrl = defaultAppRepositoryUrl;
    }
}
