package com.example.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Centralised configuration loaded from environment variables or application.yml.
 */
@Configuration
public class Config {

    @Value("${SERVER_PORT:8080}")
    private int serverPort;

    @Value("${SPRING_DATASOURCE_URL:jdbc:h2:mem:blogdb}")
    private String datasourceUrl;

    @Value("${SPRING_DATASOURCE_USERNAME:sa}")
    private String datasourceUsername;

    @Value("${SPRING_DATASOURCE_PASSWORD:}")
    private String datasourcePassword;

    public int getServerPort() {
        return serverPort;
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public String getDatasourceUsername() {
        return datasourceUsername;
    }

    public String getDatasourcePassword() {
        return datasourcePassword;
    }
}