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
