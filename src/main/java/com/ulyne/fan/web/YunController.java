package com.ulyne.fan.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fanwei_last on 2017/11/2.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/hello")
public class YunController {

    @RequestMapping(value = "/index")
    public String index(){

        return "hello index page";
    }
}
