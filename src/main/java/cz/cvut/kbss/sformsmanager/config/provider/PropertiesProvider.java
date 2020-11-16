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

    public void setRepositoryUrl( String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PropertiesProvider)) return false;
        final PropertiesProvider other = (PropertiesProvider) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$driver = this.getDriver();
        final Object other$driver = other.getDriver();
        if (this$driver == null ? other$driver != null : !this$driver.equals(other$driver)) return false;
        final Object this$repositoryUrl = this.getRepositoryUrl();
        final Object other$repositoryUrl = other.getRepositoryUrl();
        if (this$repositoryUrl == null ? other$repositoryUrl != null : !this$repositoryUrl.equals(other$repositoryUrl))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PropertiesProvider;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $driver = this.getDriver();
        result = result * PRIME + ($driver == null ? 43 : $driver.hashCode());
        final Object $repositoryUrl = this.getRepositoryUrl();
        result = result * PRIME + ($repositoryUrl == null ? 43 : $repositoryUrl.hashCode());
        return result;
    }

    public String toString() {
        return "PropertiesProvider(driver=" + this.getDriver() + ", repositoryUrl=" + this.getRepositoryUrl() + ")";
    }
}
