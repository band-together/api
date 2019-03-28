package com.band.api.controller;

import com.band.api.dto.HelloWorldDto;
import com.band.api.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    private final HelloWorldService helloWorldService;

    @Autowired
    public HelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping("/")
    public HelloWorldDto helloWorld() {
        return helloWorldService.createHelloDto();
    }
}
