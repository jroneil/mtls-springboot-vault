package com.rdrcelic.mtls.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController
{
    @PreAuthorize("hasAuthority('ROLE_TLS_CLIENT')")
    @GetMapping(value = "/user")
    public UserDetails userDetails(Principal principal) {
        return (UserDetails)((Authentication) principal).getPrincipal();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/hello")
    public String sayHello(Principal principal) {
        String username = ((UserDetails)((Authentication) principal).getPrincipal()).getUsername();
        return "Hello " + (StringUtils.isEmpty(username) ? "World" : username);
    }
}
