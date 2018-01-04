package com.rdrcelic.mtls.client.controller;

import com.rdrcelic.mtls.client.service.UserService;
import com.rdrcelic.mtls.domain.MtlsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    UserService userService;

    /**
     * Get user info from server
     * @return
     */
    @GetMapping(value = "/user")
    public MtlsUser getUser() {
        return userService.getUser();
    }

    @GetMapping(value = "/tm/{delay}")
    public String getUserSlow(@PathVariable int delay) {
        return userService.getDataSlow(delay);
    }
 }
