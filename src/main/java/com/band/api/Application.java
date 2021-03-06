package com.band.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.band.api")
@SpringBootApplication(
        exclude = {SecurityAutoConfiguration.class,
                ManagementWebSecurityAutoConfiguration.class
        })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
