package com.blaife;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
// MapperScan mapper监视器 有了这个注释后mapper层不需要再写@mapper
@MapperScan("com.blaife.mapper")
// 监视所有@Component类 主要用于过滤器监听器 （session监听器目前还存在问题）
// @ServletComponentScan(basePackages = "com.blaife.*")
public class SpringbootExampleApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootExampleApplication.class, args);
        
    }

}
