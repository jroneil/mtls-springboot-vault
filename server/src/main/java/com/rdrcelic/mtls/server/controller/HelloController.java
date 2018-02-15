package com.rdrcelic.mtls.server.controller;

import com.rdrcelic.mtls.domain.MtlsUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class HelloController
{
    @PreAuthorize("hasAuthority('ROLE_TLS_CLIENT')")
    @GetMapping(value = "/user")
    public MtlsUser userDetails(Principal principal) {
        log.debug("returning client id {}", principal.getName());
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        return MtlsUser.builder().username(userDetails.getUsername())
                .authorities(
                        userDetails.getAuthorities()
                                .stream()
                                .map(authority -> authority.getAuthority())
                                .collect(Collectors.toList()))
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/hello")
    public String sayHello(Principal principal) {
        String username = principal != null ?
                ((UserDetails)((Authentication) principal).getPrincipal()).getUsername() :
                "ANONYMOUS";
        return "Hello " + (StringUtils.isEmpty(username) ? "World" : username);
    }

    @GetMapping(value = "/slowdata/{delay}")
    public String slowData(@PathVariable int delay) throws InterruptedException {
        Thread.sleep(delay);
        return "This is slow data";
    }
}
