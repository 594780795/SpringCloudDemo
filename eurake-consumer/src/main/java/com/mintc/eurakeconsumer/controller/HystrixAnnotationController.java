package com.mintc.eurakeconsumer.controller;

import com.mintc.eurakeconsumer.service.HystrixAnnotationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HystrixAnnotationController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HystrixAnnotationService hystrixAnnotationService;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "errorMethod")
    public String test(String name) {
        System.out.println("22222");
        return restTemplate.getForObject("http://eureka-provider/search/1", String.class);
    }

    public String errorMethod(String name) {
        return "errorMethodController";
    }

}
