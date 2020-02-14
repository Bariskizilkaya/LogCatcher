package com.kocakgiller.ss.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String service(@RequestBody String data) {
        System.out.println("Data is : " + data);
        return "Cevap";
    }
}
