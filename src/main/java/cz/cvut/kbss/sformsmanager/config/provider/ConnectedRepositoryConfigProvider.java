package cz.cvut.kbss.sformsmanager.config.provider;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@PropertySource("classpath:connection.properties")
public class ConnectedRepositoryConfigProvider {

    private List<ConnectedRepositoryConfig> connections;
    private Map<String, ConnectedRepositoryConfig> connectionMap;

    public ConnectedRepositoryConfigProvider() {
    }

    public ConnectedRepositoryConfigProvider(List<ConnectedRepositoryConfig> connections, Map<String, ConnectedRepositoryConfig> connectionMap) {
        this.connections = connections;
        this.connectionMap = connectionMap;
    }

    public static class ConnectedRepositoryConfig {
        private String name;
        private String formGenRepositoryUrl;
        private String formGenServiceUrl;
        private String appRepositoryUrl;

        public ConnectedRepositoryConfig() {
        }

        public ConnectedRepositoryConfig(String name, String formGenRepositoryUrl, String formGenServiceUrl, String appRepositoryUrl) {
            this.name = name;
            this.formGenRepositoryUrl = formGenRepositoryUrl;
            this.formGenServiceUrl = formGenServiceUrl;
            this.appRepositoryUrl = appRepositoryUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFormGenRepositoryUrl() {
            return formGenRepositoryUrl;
        }

        public void setFormGenRepositoryUrl(String formGenRepositoryUrl) {
            this.formGenRepositoryUrl = formGenRepositoryUrl;
        }

        public String getFormGenServiceUrl() {
            return formGenServiceUrl;
        }

        public void setFormGenServiceUrl(String formGenServiceUrl) {
            this.formGenServiceUrl = formGenServiceUrl;
        }

        public String getAppRepositoryUrl() {
            return appRepositoryUrl;
        }

        public void setAppRepositoryUrl(String appRepositoryUrl) {
            this.appRepositoryUrl = appRepositoryUrl;
        }
    }

    public Map<String, ConnectedRepositoryConfig> getConnectionMap() {
        if (connectionMap == null) {
            connectionMap = new HashMap<>();

            if (connections == null) {
                connections = new ArrayList<>();
                connections.add(new ConnectedRepositoryConfig(
                        "study-manager",
                        "http://onto.fel.cvut.cz/rdf4j-server/repositories/fss-study-dev-formgen",
                        "https://kbss.felk.cvut.cz/s-pipes-vfn-dev/service?_pId=clone-fss-form",
                        "http://onto.fel.cvut.cz/rdf4j-server/repositories/fss-study-dev-app"));
            }

            connections.forEach(c -> {
                connectionMap.put(c.getName(), c);
            });
        }
        return connectionMap;
    }

    public List<ConnectedRepositoryConfig> getConnections() {
        return connections;
    }

    public void setConnections(List<ConnectedRepositoryConfig> connections) {
        this.connections = connections;
    }

    public void setConnectionMap(Map<String, ConnectedRepositoryConfig> connectionMap) {
        this.connectionMap = connectionMap;
    }

}
