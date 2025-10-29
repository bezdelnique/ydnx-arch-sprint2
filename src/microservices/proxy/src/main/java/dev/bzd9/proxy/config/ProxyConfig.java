package dev.bzd9.proxy.config;

import lombok.Data;
import lombok.Getter;
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
