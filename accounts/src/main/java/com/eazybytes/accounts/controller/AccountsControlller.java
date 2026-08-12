package com.eazybytes.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsControlller {

    @GetMapping("/sayHello")
    public String sayHello() {
        return "hello world from Spring Boot";
    }

}
