# Spring-Boot-Filter
springboot中使用过滤器是十分简单的，相较于spring来说减少了xml文件的配置。一个class就可以实现过滤器了。

## 启动类注解 @ServletComponentScan
使用此注解后Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册，无需其他代码。

## 过滤器类
```java
/**
 * 测试过滤器
 */
@Order(2) // 不起作用，因为注册Filter的顺序是由类名的顺序决定的。
// 解决方法有：修改filter目录中对应过滤器的文件名按首字母顺序调整自己想要的顺序即可！！！
// 使用FilterRegistrationBean 来设置Order定义过滤器顺序。

// urlPatterns：拦截路径
// filterName：过滤器名称
// initParams：初始化内容 初始化url为http://localhost:8080
@WebFilter(urlPatterns = "/*", filterName = "testFilter", initParams = {
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
        filterChain.doFilter(servletRequest, servletResponse); // 此方法是过滤器验证完成继续执行
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
```
简单的说一下操作流程就是，自定义过滤器名称，实现Filter接口，重写方法（重点是doFilter方法）。添加注解@WebFilter。这样一个过滤器就完成了。

## @Order
细心的朋友应该发现了，@Order注解不可用，添加之后没有效果，查询结果显示：因为@WebFilter本身是没有Order属性，所以构建的Filter将是默认的Order值。  
那么，@Order注解有什么用？ （@Order注解主要用来控制配置类的加载顺序）

下面使用FilterRegistrationBean方式来实现自定义过滤器顺序。

## FilterRegistrationBean
先自定义两个过滤器，注意，不需要@Filter注解。在doFilter方法中加入一些输入带来判断是哪个过滤器执行了。（不贴代码了，详看testOrder包下的内容）。
定义配置文件，代码如下：
```java
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
```
如上代码，实现WebMvcConfigurer接口，添加@Configuration注解，添加Bean，类型为FilterRegistrationBean，在方法中配置优先级。
此时再进行测试，发现优先级可自定义。  
注意：使用此方法实现过滤器则不需要在启动类中添加注解@ServletComponentScan。