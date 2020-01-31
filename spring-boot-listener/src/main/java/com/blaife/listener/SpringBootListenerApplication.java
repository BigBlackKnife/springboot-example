package com.blaife.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan // Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册，无需其他代码。
@SpringBootApplication
public class SpringBootListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootListenerApplication.class, args);
    }

}
