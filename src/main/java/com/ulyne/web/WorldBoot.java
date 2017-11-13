package com.ulyne.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fanwei_last on 2017/10/27.
 */
@RestController
@EnableAutoConfiguration
public class WorldBoot {

    @RequestMapping(value = "/world")
    public String index(){

        return "this is world!";
    }

    public static void main(String[] args){
        SpringApplication.run(WorldBoot.class, args);
    }
}
