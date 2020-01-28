package com.blaife.filter.filter;


import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 测试过滤器
 */
@Order(2) // 不起作用，因为注册Filter的顺序是由类名的顺序决定的。
// 解决方法有：修改filter目录中对应过滤器的文件名按首字母顺序调整自己想要的顺序即可！！！
// 使用FilterRegistrationBean 来设置Order定义过滤器顺序。（太过繁琐）

// urlPatterns：拦截路径
// filterName：过滤器名称
// initParams：初始化内容 初始化url为http://localhost:8080
@WebFilter(urlPatterns = "/test/*", filterName = "testFilter", initParams = {
        @WebInitParam(name = "URL", value = "http://localhost:8080/blaife/test/testFilter")})
public class TestFilter implements Filter {


    /**
     * 可以初始化Filter在web.xml里面配置的初始化参数
     * filter对象只会创建一次，init方法也只会执行一次。
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String url = filterConfig.getInitParameter("URL");
        System.out.println("我是testFilter的初始化方法！URL=" + url +  "，生活开始.........");
    }

    /**
     * 主要业务代码编写方法
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("我是testFilter过滤器的执行方法，客户端向Servlet发送的请求被我拦截到了");
        System.out.println(((HttpServletRequest)servletRequest).getRequestURI()); // 方法路径 如：/testFilter
        System.out.println(((HttpServletRequest)servletRequest).getRequestURL()); // 绝对路径 如：http://localhost:8080/testFilter
        filterChain.doFilter(servletRequest, servletResponse); // 此方法是离开过滤器继续执行
        System.out.println("我是testFilter过滤器的执行方法，Servlet向客户端发送的响应被我拦截到了");
    }

    /**
     * 在销毁Filter时自动调用。
     */
    @Override
    public void destroy() {
        System.out.println("我是testFilter过滤器的被销毁时调用的方法！，活不下去了................" );
    }
}
