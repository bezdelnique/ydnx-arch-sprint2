package dev.bzd9.proxy.controller;

import dev.bzd9.proxy.config.ProxyConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;

@Slf4j
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

    @RequestMapping({"/api/movies", "/api/movies/health"})
    public ResponseEntity<Object> proxyMovies(HttpServletRequest request) {
        double percent = proxyConfig.getMoviesMigrationPercent();
        double threshold = percent / 100.0;
        boolean routedToNew = random.nextDouble() < threshold;

        String url = routedToNew ?
                proxyConfig.getMoviesServiceUrl() :
                proxyConfig.getMonolithUrl();

        return doProxy(url, request);
    }

    @RequestMapping("/api/users")
    public ResponseEntity<Object> proxyUsers(HttpServletRequest request) {
        return doProxy(proxyConfig.getMonolithUrl(), request);
    }

    @RequestMapping("/api/payments")
    public ResponseEntity<Object> proxyPayments(HttpServletRequest request) {
        return doProxy(proxyConfig.getMonolithUrl(), request);
    }

    @RequestMapping("/api/subscriptions")
    public ResponseEntity<Object> proxySubscriptions(HttpServletRequest request) {
        return doProxy(proxyConfig.getMonolithUrl(), request);
    }

    private ResponseEntity<Object> doProxy(String url, HttpServletRequest request) {
        if (request.getMethod().equals("POST")) {
            return doPostProxy(url, request);
        } else if (request.getMethod().equals("GET")) {
            return doGetProxy(url, request.getRequestURI(), request.getQueryString());
        }
        throw new RuntimeException("Unsupported HTTP method: " + request.getMethod());
    }

    private ResponseEntity<Object> doGetProxy(String url, String uri, String qs) {
        qs = qs != null && !qs.isEmpty() ? "?" + qs : "";
        String fullUrl = url + uri + qs;
        log.info("Proxy GET: {}", fullUrl);

        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.GET,
                    null,
                    byte[].class
            );

            HttpHeaders headers = new HttpHeaders();
            response.getHeaders().forEach((key, valueList) -> {
                if (!key.equalsIgnoreCase("Transfer-Encoding") &&
                        !key.equalsIgnoreCase("Connection")) {
                    headers.put(key, valueList);
                }
            });

            return ResponseEntity
                    .status(response.getStatusCode())
                    .headers(headers)
                    .body(response.getBody());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Proxy error: " + e.getMessage()));
        }
    }

    private ResponseEntity<Object> doPostProxy(String urlBase, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String qs = request.getQueryString();
        qs = qs != null && !qs.isEmpty() ? "?" + qs : "";
        String fullUrl = urlBase + uri + qs;
        log.info("Proxy POST: {}", fullUrl);

        final String body;
        try {
            body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Forward headers
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.add(name, request.getHeader(name));
        }
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.POST,
                    entity,
                    byte[].class
            );

            return ResponseEntity
                    .status(response.getStatusCode())
                    .headers(response.getHeaders())
                    .body(response.getBody());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Proxy error: " + e.getMessage()));
        }
    }

}
