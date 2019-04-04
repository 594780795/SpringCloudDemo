package com.mintc.eurakeconsumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HystrixAnnotationService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "errorMethod")
    public String test(String name) {
        return restTemplate.getForObject("http://eureka-provider/search/1", String.class);
    }

    public String errorMethod(String name) {
        return "errorMethodService";
    }

}
