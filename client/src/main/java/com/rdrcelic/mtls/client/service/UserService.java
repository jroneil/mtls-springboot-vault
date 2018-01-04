package com.rdrcelic.mtls.client.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rdrcelic.mtls.client.util.UrlComposer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.util.Collections;

/**
 * Service provides user info from backend
 */
@Slf4j
@Service
public class UserService {

    // path to user backend (server)
    private static String userBackend = "https://localhost:8443/user";
    // path to slow backend
    private static String slowBackend = "https://localhost:8443/slowdata/{delay:[0-9]{1,6}}";

    RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    /**
     * Get user info from server
     * @return
     */
    public String getUser() {
        log.debug("getting user...");
        ResponseEntity<String> rs = restTemplate.getForEntity(userBackend, String.class);
        return rs.toString();
    }

    @HystrixCommand(fallbackMethod = "getDataSlowFallback", commandKey = "userServiceCommand")
    public String getDataSlow(int delay) {
        log.debug("getting data, delay {}", delay);
        String url = UrlComposer.buildUri(slowBackend, Collections.singletonMap("delay", delay)).toUriString();
        ResponseEntity<String> rs = restTemplate.getForEntity(url, String.class);
        log.debug("got data from slow backend, delay {}", delay);
        return rs.toString();
    }

    private String getDataSlowFallback(int delay) {
        log.debug("fallback, delay {}", delay);
        return "Some fallback data";
    }
}
