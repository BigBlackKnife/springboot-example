package com.blaife.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * request请求监听
 */
public class RequestListenter implements ServletRequestListener {

    private final Logger logger = LoggerFactory.getLogger(RequestListenter.class);

    /**
     * 请求销毁
     * @param sre
     */
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        logger.info("this is RequestListenter:请求销毁");
    }

    /**
     * 请求创建
     * @param sre
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        logger.info("this is RequestListenter:请求创建");
    }
}
