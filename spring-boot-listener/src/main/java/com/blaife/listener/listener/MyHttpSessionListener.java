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

