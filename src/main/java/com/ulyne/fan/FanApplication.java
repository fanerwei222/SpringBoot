package com.ulyne.fan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ulyne") //扫描该文件夹下面的所有控制器什么的
@MapperScan("com.ulyne.mybatis.mapper")//对mybatis mapper文件夹进行扫描
public class FanApplication {

	public static void main(String[] args) {
		SpringApplication.run(FanApplication.class, args);
	}
}
