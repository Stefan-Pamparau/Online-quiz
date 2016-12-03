package com.iquest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.iquest")
public class ServerBoot {

    public static void main(String[] args) {
        SpringApplication.run(ServerBoot.class, args);
    }
}
