package com.publicapi.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serviceapi")
public class HelloWorld {

    @GetMapping("/hello")
    public String helloworld(String name){
        return "hello, "+name;
    }
}
