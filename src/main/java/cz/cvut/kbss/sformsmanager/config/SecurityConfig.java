package cz.cvut.kbss.sformsmanager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "cz.cvut.kbss.sformsmanager")
public class SecurityConfig {

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
    // We're allowing all methods from all origins so that the application API is usable also by other clients
    // than just the UI.
    // This behavior can be restricted later.
//        final CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
//        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
//        corsConfiguration.addExposedHeader(HttpHeaders.AUTHORIZATION);
//        corsConfiguration.addExposedHeader(HttpHeaders.LOCATION);
//        corsConfiguration.addExposedHeader(HttpHeaders.CONTENT_DISPOSITION);
//        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
//        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
//        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
//
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
}
