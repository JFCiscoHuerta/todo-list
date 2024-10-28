package com.gklyphon.ToDo.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing the Jackson ObjectMapper.
 * <p>
 * This class is responsible for creating and configuring a
 * {@link ObjectMapper} bean with the necessary modules for
 * serializing and deserializing Java 8 time types.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
@Configuration
public class JacksonConfig {

    /**
     * Creates a new instance of {@link ObjectMapper} and registers
     * the {@link JavaTimeModule} to support Java 8 date and time types.
     *
     * @return a configured {@link ObjectMapper} instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
