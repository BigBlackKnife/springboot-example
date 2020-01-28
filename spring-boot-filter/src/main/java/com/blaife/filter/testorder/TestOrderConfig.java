package com.blaife.filter.testorder;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TestOrderConfig implements WebMvcConfigurer {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean filterOne() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new TestOrderFilterOne()); // 指定过滤器
        frBean.addUrlPatterns("/testOrder"); // 设置过滤条件
        frBean.setOrder(1); // 设置优先级
        return frBean;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean filterTwo() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new TestOrderFilterTwo()); // 指定过滤器
        frBean.addUrlPatterns("/testOrder"); // 设置过滤条件
        frBean.setOrder(0); // 设置优先级
        return frBean;
    }

}
