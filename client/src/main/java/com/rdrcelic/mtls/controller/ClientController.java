package com.rdrcelic.mtls.controller;

import com.rdrcelic.mtls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/user")
    public String getUser() {
        return userService.getUser();
    }

}
