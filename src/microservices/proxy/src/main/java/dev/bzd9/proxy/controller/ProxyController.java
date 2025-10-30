package dev.bzd9.proxy.controller;

import dev.bzd9.proxy.config.ProxyConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Random;

@RestController
public class ProxyController {
    private final RestTemplate restTemplate;
    private final ProxyConfig proxyConfig;
    private final Random random = new Random();

    public ProxyController(RestTemplate restTemplate, ProxyConfig proxyConfig) {
        this.restTemplate = restTemplate;
        this.proxyConfig = proxyConfig;

    }

    @GetMapping("/health")
    public Map<String, String> getHealthStatus() {
        return Map.of("status", "true");
    }

    @GetMapping("/api/movies")
    public ResponseEntity<Object> getMovies(HttpServletRequest request) {
        double probability = (double) proxyConfig.getMoviesMigrationPercent() / 100;
        double randomValue = random.nextDouble();
        String url = (randomValue < probability) ? proxyConfig.getMonolithUrl() : proxyConfig.getMoviesServiceUrl();
        return doGetProxy(url, request.getRequestURI());
    }

    @GetMapping("/api/users")
    public ResponseEntity<Object> getUsers(HttpServletRequest request) {
        return doGetProxy(proxyConfig.getMonolithUrl(), request.getRequestURI());
    }

    private ResponseEntity<Object> doGetProxy(String url, String uri) {
        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    url + uri,
                    HttpMethod.GET,
                    null,
                    byte[].class
            );

            return ResponseEntity
                    .status(response.getStatusCode())
                    .headers(response.getHeaders())
                    .body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Proxy error: " + e.getMessage()));
        }
    }

}
