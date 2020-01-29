# spring-boot-interceptor
使用效果与filter相似，但是他们之间还是有差异的，最关键的是：Filter是servlet规范中定义的java web组件, interceptor是Spring提供的组件。  
Filter是在Java Web发展早期使用比较多的过滤器，随着Spring的发展和兴起，则Interceptor随着Spring而逐渐更为大家所熟知和使用。

## 自定义拦截器类
```java
package com.blaife.interceptor.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestInterceptor implements HandlerInterceptor {

    /**
     * 请求处理前调用，通过返回true，不通过返回false
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("this is preHandle");
        return true;
    }

    /**
     * 请求处理后，视图渲染前调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("this is postHandle");
    }

    /**
     * 整个请求结束后调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("this is afterCompletion");
    }
}
```
实现HandlerInterceptor类，主要有三个方法，在一个请求执行流程的不同阶段触发。最常用的是preHandle方法，在业务层执行前进行拦截处理。

## 注册拦截器bean
```java
package com.blaife.interceptor.configurer;

import com.blaife.interceptor.interceptor.TestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptionConfigurer implements WebMvcConfigurer {

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TestInterceptor()).addPathPatterns("/test/**");
    }
}
```
重写WebMvcConfigurer类的addInterceptors方法。一个方法中可以一次性注册多个拦截器。
在springboot2中我们使用拦截器编写这两个类就可以了，这是在执行请求时，对应的拦截器就会起作用了。