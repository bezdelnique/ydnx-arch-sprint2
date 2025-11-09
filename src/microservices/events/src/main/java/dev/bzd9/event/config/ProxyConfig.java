package dev.bzd9.event.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "proxy")
@Data
public class ProxyConfig {
    private int moviesMigrationPercent;
    private String moviesServiceUrl;
    private String monolithUrl;
}
