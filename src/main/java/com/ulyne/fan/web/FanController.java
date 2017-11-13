package com.ulyne.fan.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fanwei_last on 2017/11/2.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/index")
public class FanController {

    @RequestMapping(value = "/index")
    public String index(){

        return "index index page";
    }

}
