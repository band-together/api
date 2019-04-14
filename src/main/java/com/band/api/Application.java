package com.band.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@EnableJpaRepositories(basePackages = "com.band.api")
@SpringBootApplication(
        exclude = { SecurityAutoConfiguration.class,
                    ManagementWebSecurityAutoConfiguration.class //Apparently something imports actuate for some unknown reason so we need to exclude that too
        })


public class Application {

    public static void main(String[] args) {
        log.info("test");
        SpringApplication.run(Application.class, args);
    }
}
