package com.iquest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "iquest")
//@EntityScan(basePackages = "model")
public class ServerBoot {

    public static void main(String[] args) {
        SpringApplication.run(ServerBoot.class, args);
    }
}
