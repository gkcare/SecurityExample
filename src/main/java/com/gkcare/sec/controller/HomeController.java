package com.gkcare.sec.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "Welcome to Spring Boot Security";
    }

    @RequestMapping(method = RequestMethod.GET,path = "/test")
    public String a(){
        return "Welcome to Spring Boot Security";
    }
}
