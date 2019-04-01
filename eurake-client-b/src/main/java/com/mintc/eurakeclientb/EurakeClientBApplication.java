package com.mintc.eurakeclientb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurakeClientBApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurakeClientBApplication.class, args);
    }

}
