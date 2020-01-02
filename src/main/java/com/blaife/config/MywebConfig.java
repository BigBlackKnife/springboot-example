package com.blaife.config;

import com.blaife.filter.MyFilter;
import com.blaife.interceptor.MyInterceptor;
import com.blaife.listener.MyHttpSessionListener;
import com.blaife.listener.RequestListenter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 总配置信息
 */
@Configuration
public class MywebConfig implements WebMvcConfigurer {

    /**
     * 过滤器配置信息
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean filterRegist() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new MyFilter()); // 指定过滤器
        frBean.addUrlPatterns("/*"); // 设置过滤条件
        frBean.setOrder(0); // 设置优先级
        System.out.println("filter");
        return frBean;
    }

    /**
     * 监听器配置信息
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public ServletListenerRegistrationBean listenerRegist() {
        ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
        srb.setListener(new RequestListenter());
        System.out.println("listener");
        return srb;
    }

    /**
     * 拦截器配置信息
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("interceptors");
        // 拦截路径载书写是需要是从项目访问路径后开始
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/message/asd/*");
    }
}
