# Spring-Boot-Listener
springboot监听器的类型有很多，比如监听Servlet上下文用来初始化一些数据、监听HTTP Session用来获取当前在线的人数、监听客户端请求的ServletRequest对象来获取用户的访问信息等。

## 1.ApplicationListener<ContextRefreshedEvent>初始化信息

### 模拟一个从数据库查询数据的方法
```java
@Service
public class UserService {

    /**
     * 模拟从数据库中获取数据
     * @return
     */
    public User getUser() {
        System.out.println("this is getUser Method");
        return new User("blaife", Math.random() + " ", "blaife01");
    }

}
```

### 监听器具体内容
```java
package com.blaife.listener.listener;

import com.blaife.listener.model.User;
import com.blaife.listener.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * 使用 ApplicationListener<ContextRefreshedEvent> 实现系统启动时加载初始化信息
 */
@Component
public class MyServletContextListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MyServletContextListener.class);

    @Autowired
    UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 获取到 Application 上下文
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        User user = userService.getUser();
        System.out.println(user);
        logger.info(user.toString());
        // 获取 application 域对象，将查到的信息放到 application 域中
        ServletContext application = applicationContext.getBean(ServletContext.class);
        application.setAttribute("user", user);
    }
}
```
此方法在项目初始化启动时触发，将查询到的信息放在application域中。 在项目启动时就可以看到输出的信息了。  
此类上的注解需要使用@Component而不是@WebListener,尝试使用@WebListener会启动报错，内置tomcat启动错误。

## 2.HttpSessionListener监听在线人数
```java
package com.blaife.listener.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

/**
 * 使用 HttpSessionListener 统计在线人数和在线用户
 * HttpSessionListener 监听session对象的创建以及销毁
 * 可实现功能防止用户重复登录，统计用户在线数量，统计用户登录频率
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    private static final Logger logger = LoggerFactory.getLogger(MyHttpSessionListener.class);

    /**
     * 记录在线的用户数量
     */
    public Integer count = 0;


    /**
     * 添加session
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        // 获取session ID
        String sessionId = session.getId();
        count++;
        logger.info("用户" + sessionId + "上线了， 当前用户数量：" +count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        count--;
        logger.info("用户" + se.getSession().getId() + "下线了， 当前用户数量：" +count);
    }
}
```
此类中有两个可重写方法sessionCreated和sessionDestroyed，分别是session创建和session销毁。用来监听当前登陆人数。  
测试方法：
```java
@RestController
public class TestController {
    /**
     * 添加用户blaife
     * @return
     */
    @RequestMapping("/addSessionBlaife")
    public String addSessionBlaife(HttpSession session) {
        session.setAttribute("UserBlaife", "blaife");
        return "用户blaife登陆";
    }

    /**
     * 销毁当前session会话信息
     * @param session
     * @return
     */
    @RequestMapping("/sessionInvaildate")
    public String sessionInvaildate(HttpSession session) {
        session.invalidate();
        return "销毁session会话";
    }
}
```
此监听器监听的是session会话，并非session对象。测试时打开两个浏览器，分别执行addSessionBlaife，会看到当前登录人数添加。
分别执行sessionInvaildate，会看到当前登陆人数减少。

## 3.HttpSessionAttributeListener监听session属性变化
```java
package com.blaife.listener.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * 检测HttpSession中属性变化，采取相应措施
 * HttpSessionAttributeListener 对session对象属性改变的监听。
 */
@WebListener
public class MyHttpSessionAttributeListener implements HttpSessionAttributeListener {

    private static final Logger logger = LoggerFactory.getLogger(MyHttpSessionAttributeListener.class);

    /**
     * 添加httpSession
     * @param se
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        logger.info("增加了httpSession " + se.getName() + ":" + se.getValue());
    }

    /**
     * 修改httpSession
     * @param se
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        logger.info("修改了httpSession " + se.getName() + ":" + se.getValue());
    }

    /**
     * 移除httpSession
     * @param se
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        logger.info("移除了httpSession " + se.getName() + ":" + se.getValue());
    }
}
```
此类中有三个方法attributeAdded，attributeReplaced，attributeRemoved，分别session属性的添加，修改，移除。
可通过HttpSessionBindingEvent对象直接访问当前监听到的session的name和value。  
测试方法：
```java
@RestController
public class TestController {
    /**
     * 添加用户blaife
     * @return
     */
    @RequestMapping("/addSessionBlaife")
    public String addSessionBlaife(HttpSession session) {
        session.setAttribute("UserBlaife", "blaife");
        return "用户blaife登陆";
    }

    /**
     * 移除用户blaife
     * @return
     */
    @RequestMapping("/removeSessionBlaife")
    public String removeSessionBlaife(HttpSession session) {
        session.removeAttribute("UserBlaife");
        return "用户blaife注销";
    }
}
```
第一次执行addSessionBlaife方法会输出session创建，第二次执行会提示session修改，执行removeSessionBlaife提示session移除。

## 4.ServletRequestListener监听客户端请求Servlet Request对象
```java
package com.blaife.listener.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * ServletRequestListener 监听器 记录用户访问路径
 */
@WebListener
public class MyServletRequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
       System.out.println("request start");
       HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
       System.out.println("session id 为" + request.getRequestedSessionId());
       System.out.println("request url 为" + request.getRequestURL());
       request.setAttribute("name", "blaife");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("request end");
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        System.out.println("request域中保存的name值为" + request.getAttribute("name"));
    }

}
```
监听任何客户端请求，分别是请求初始化和请求销毁。用户获取用户的访问信息。

## 5.自定义监听器
我们使用ApplicationListener接口，他需要一个event对象。首先来写一下我们的event对象。  
```java
package com.blaife.listener.event;

import com.blaife.listener.model.User;
import org.springframework.context.ApplicationEvent;

public class MyCustomerEvent extends ApplicationEvent {

    private User user;

    public MyCustomerEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
```
其实就是一个普通类实现了ApplicationEvent接口。下面来写一下我们自定义的监听器
```java
package com.blaife.listener.listener;

import com.blaife.listener.event.MyCustomerEvent;
import com.blaife.listener.model.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 自定义监听器，监听myCustomerEvent事件
 */
@Component
public class MyCustomEventListener implements ApplicationListener<MyCustomerEvent> {
    @Override
    public void onApplicationEvent(MyCustomerEvent myCustomerEvent) {
        // 把事件中的信息获取到
        User user = myCustomerEvent.getUser();
        // 处理事件，实际项目中可以通知别的微服务或者处理其他逻辑等
        System.out.println("用户名：" + user.getName());
        System.out.println("年龄：" + user.getAge());
        System.out.println("密码：" + user.getPwd());
    }
}
```
依旧使用ApplicationListener接口，只不过泛型使用我们自定义的event:MyCustomerEvent,重写一下必须的方法onApplicationEvent。
当我们调用这个事件时自动触发这个监听器。
```java
@Service
public class UserService {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 测试自定义监听器的使用效果
     * @return
     */
    public User getUserTestCustomerLintener() {
        User user = new User("blaife", Math.random() + " ", "blaife01");
        MyCustomerEvent event = new MyCustomerEvent(this, user);
        applicationContext.publishEvent(event);
        return user;
    }

}
```
```java
@RestController
public class TestController {

    @Autowired
    UserService userService;

    /**
     * 测试自定义监听器的使用
     * @return
     */
    @RequestMapping("/getUserTestCustomerListener")
    public User getUserTestCustomerListener() {
        return userService.getUserTestCustomerLintener();
    }
}
```
在service层调用了我们自定义的event，这样在执行此方法时，会自动触发监听器。