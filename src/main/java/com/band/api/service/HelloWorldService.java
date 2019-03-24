package com.band.api.service;

import com.band.api.dto.HelloWorldDto;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    public HelloWorldDto createHelloDto() {
        return HelloWorldDto.builder().greeting("Hello world!").build();
    }
}
