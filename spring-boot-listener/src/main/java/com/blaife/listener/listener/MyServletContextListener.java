package com.blaife.listener.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(MyServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("项目启动，执行初始化操作");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("项目关闭，执行数据备份操作");
    }
}
