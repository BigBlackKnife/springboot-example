package com.blaife.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 监听器 demo
 * listener是servlet规范中定义的一种特殊类。用于监听servletContext、HttpSession和servletRequest等域对象的创建和销毁事件
 * 监听域对象的属性发生修改的事件。用于在事件发生前、发生后做一些必要的处理。
 * 其主要可用于以下方面：1、统计在线人数和在线用户2、系统启动时加载初始化信息3、统计网站访问量4、记录用户访问路径。
 *
 *
 * session监听器目前还是不能使用，可能是由于我是用Redis-Session的问题
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    public MyHttpSessionListener() {
        System.out.println("初始化session监听器");
    }

    private final Logger logger = LoggerFactory.getLogger(MyHttpSessionListener.class);

    public static int online = 0;

    /**
     * session创建时触发
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("创建session:"+se.getSession().getId());
        online ++;
    }

    /**
     * session销毁时触发
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("销毁session:"+se.getSession().getId());
        online --;
    }

    /**
     * 创建session
     * @param se
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        logger.info("创建HttpSession:"+se.getSession().getId());
    }

    /**
     * 修改session
     * @param se
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        logger.info("修改HttpSession:"+se.getSession().getId());
    }

    /**
     * 移除session
     * @param se
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        logger.info("删除HttpSession:"+se.getSession().getId());
    }
}
