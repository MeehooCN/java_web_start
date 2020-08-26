package com.meehoo.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

/**
 * @author zc
 * @date 2020-01-03
 */
@SpringBootApplication
@ComponentScan
public class WebApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(WebApplication.class, args);
        System.out.println("-----------------启动成功了----------------");
    }
}
