package com.gklyphon.ToDo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/**
 * Configuration class for setting up security settings for the application.
 * <p>
 * This class configures HTTP security, including authorization for
 * specific endpoints and CORS (Cross-Origin Resource Sharing) settings.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method specifies which HTTP requests are permitted without
     * authentication. All GET, POST, PUT, and DELETE requests to specified
     * task-related endpoints are allowed.
     * </p>
     *
     * @param http the {@link HttpSecurity} to configure
     * @return a {@link SecurityFilterChain} instance
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (auths) -> auths
                        .requestMatchers(HttpMethod.GET, "/v1/tasks", "/v1/tasks/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/tasks/create-task").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/tasks/update-task/{id}",
                                "/v1/tasks/update-complete-task/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/v1/tasks/delete-task/{id}").permitAll()
        )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * Configures CORS settings for the application.
     * <p>
     * This method sets up allowed origins, HTTP methods, headers, and other
     * CORS-related settings, enabling cross-origin requests to the application.
     * </p>
     *
     * @return a {@link CorsConfigurationSource} instance with configured CORS settings
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(List.of("Content-Type","Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
