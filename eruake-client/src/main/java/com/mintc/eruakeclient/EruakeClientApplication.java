package com.mintc.eruakeclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EruakeClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EruakeClientApplication.class, args);
    }

}
