package com.blaife.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * request请求监听
 */
@WebListener
public class RequestListenter implements ServletRequestListener {

    /**
     * 请求销毁
     * @param sre
     */
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("---------------------------->请求销毁");

    }

    /**
     * 请求创建
     * @param sre
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("---------------------------->请求创建");
    }
}
