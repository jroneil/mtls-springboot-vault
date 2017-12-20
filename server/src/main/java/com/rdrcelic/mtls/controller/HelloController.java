package com.rdrcelic.mtls.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class HelloController
{
    @PreAuthorize("hasAuthority('ROLE_TLS_CLIENT')")
    @GetMapping(value = "/user")
    public UserDetails userDetails(Principal principal) {
        log.debug("returning client id,.,");
        return (UserDetails)((Authentication) principal).getPrincipal();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/hello")
    public String sayHello(Principal principal) {
        String username = ((UserDetails)((Authentication) principal).getPrincipal()).getUsername();
        return "Hello " + (StringUtils.isEmpty(username) ? "World" : username);
    }

    @GetMapping(value = "/slowdata/{delay}")
    public String slowData(@PathVariable int delay) throws InterruptedException {
        Thread.sleep(delay);
        return "This is slow data";
    }
}
