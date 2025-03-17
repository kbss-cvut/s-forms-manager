package cz.cvut.kbss.sformsmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SFormsManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SFormsManagerApplication.class, args);
    }
}
