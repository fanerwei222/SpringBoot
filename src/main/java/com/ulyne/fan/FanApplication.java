package com.ulyne.fan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ulyne.fan")
public class FanApplication {

	public static void main(String[] args) {
		SpringApplication.run(FanApplication.class, args);
	}
}
