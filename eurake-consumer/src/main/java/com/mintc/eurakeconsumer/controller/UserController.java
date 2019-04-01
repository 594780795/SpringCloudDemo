package com.mintc.eurakeconsumer.controller;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Configuration
public class UserController extends CommonController{

//    @GetMapping(value = "/router")
//    @ResponseBody
//    public String router(){
//        RestTemplate temp = getRestTemplate();
//        return temp.getForObject("http://eureka-provider/search/1", String.class);
//    }
}
