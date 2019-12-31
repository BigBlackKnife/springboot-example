package com.example.springbootexample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springbootexample.*.mapper")
public class SpringbootExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootExampleApplication.class, args);
    }

}
