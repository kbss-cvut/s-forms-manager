package cz.cvut.kbss.sformsmanager.config.provider;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Configuration
@PropertySource("classpath:connection.properties")
@ConfigurationProperties(prefix = "repository")
public class ConnectedRepositoryConfigProvider {

    @Getter(AccessLevel.PRIVATE)
    private List<ConnectedRepositoryConfig> connections;
    private Map<String, ConnectedRepositoryConfig> connectionMap;

    @Data
    public static class ConnectedRepositoryConfig {
        private String name;
        private String formGenRepositoryUrl;
        private String formGenServiceUrl;
        private String appRepositoryUrl;
    }

    public Map<String, ConnectedRepositoryConfig> getConnectionMap() {
        if (connectionMap == null) {
            connectionMap = new HashMap<>();
            connections.forEach(c -> {
                connectionMap.put(c.getName(), c);
            });
        }
        return connectionMap;
    }
}
