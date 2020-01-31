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
public class MyServletContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MyServletContextRefreshedListener.class);

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
