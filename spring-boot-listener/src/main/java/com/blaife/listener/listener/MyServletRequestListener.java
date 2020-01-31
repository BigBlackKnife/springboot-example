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
