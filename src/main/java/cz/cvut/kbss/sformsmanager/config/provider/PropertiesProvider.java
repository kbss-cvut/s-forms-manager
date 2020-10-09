package cz.cvut.kbss.sformsmanager.config.provider;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesProvider {

    @NotNull
    @Value("${driver}")
    private String driver;

    @NotNull
    @Value("${repositoryUrl}")
    private String repositoryUrl;

}
