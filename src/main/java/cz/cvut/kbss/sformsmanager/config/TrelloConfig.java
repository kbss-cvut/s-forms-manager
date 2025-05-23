package cz.cvut.kbss.sformsmanager.config;

import com.julienvey.trello.impl.http.ApacheHttpClient;
import cz.cvut.kbss.sformsmanager.service.ticketing.trello.TrelloClientWithCustomFields;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class TrelloConfig {

    @Value("${trello.key}")
    private String apiKey;

    @Value("${trello.token}")
    private String apiToken;

    public TrelloConfig() {
    }

    @Bean
    public TrelloClientWithCustomFields trelloClient() {
        return new TrelloClientWithCustomFields(apiKey, apiToken, new ApacheHttpClient());
    }
}
