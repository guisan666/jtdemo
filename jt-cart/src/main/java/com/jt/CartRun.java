package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.applet.Main;

@SpringBootApplication
@MapperScan("com.jt.mapper")
public class CartRun {
    public static void main(String[] args) {
        SpringApplication.run(CartRun.class,args);
    }
}
