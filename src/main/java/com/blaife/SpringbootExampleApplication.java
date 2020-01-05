package com.blaife;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// MapperScan mapper监视器 有了这个注释后mapper层不需要再写@mapper
@MapperScan("com.blaife.mapper")
// 一般放在配置类中，该注解会使Spring 容器中所有bean中的@Scheduled注解起作用。
@EnableScheduling
// 开启异步编程，该注解使@Async注解起作用
@EnableAsync
// 监视所有@Component类 主要用于过滤器监听器 （session监听器目前还存在问题）
// @ServletComponentScan(basePackages = "com.blaife.*")
public class SpringbootExampleApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootExampleApplication.class, args);
        
    }

}
