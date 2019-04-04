package com.mintc.eruakeclient.controller;

import com.mintc.eruakeclient.beans.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class UserController {

//    public static AtomicInteger count = new AtomicInteger(0);
    public static int count = 0;

    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(@PathVariable Integer id, HttpServletRequest request) {
        int i = 1/0;
        return "hello";
    }

    @RequestMapping(value = "/error22", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String error() {
        try {
//            count.getAndIncrement();
            count++;
            System.out.println("error22:" + count);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "error222";
    }
}
