package com.rdrcelic.mtls.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController
{
    @GetMapping(value = "/user")
    public UserDetails userDetails(Principal principal) {
        return (UserDetails)((Authentication) principal).getPrincipal();
    }
}
