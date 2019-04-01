package com.mintc.eruakeclient.controller;

import com.mintc.eruakeclient.beans.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    public static Integer count = 0;

    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person hello(@PathVariable Integer id, HttpServletRequest request) {
        Person person = new Person();
        person.setId(1);
        person.setName("mintc");
        person.setAge(99);
        person.setMessage(request.getRequestURL().toString());
        return person;
    }



    @RequestMapping(value = "/error22", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String error() {
        try {
            count ++;
            System.out.println("error22:" + count);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "error222";
    }
}
