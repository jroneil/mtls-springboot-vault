package com.rdrcelic.mtls.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private static String userBackend = "https://localhost:8443/user";

    RestTemplate restTemplate;

    public UserService(RestTemplateBuilder templateBuilder) {
        this.restTemplate = templateBuilder.build();
    }

    public String getUser() {
        ResponseEntity<String> rs = restTemplate.getForEntity(userBackend, String.class);
        return rs.toString();
    }
}
