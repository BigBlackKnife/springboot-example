package com.blaife.controller;

import com.blaife.listener.MyHttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 测试过滤器、拦截器、监听器
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Value("${application.message:Hello World}")
    private String message ;

    /**
     * 测试拦截器是否可以拦截到
     * @param name
     * @return
     */
    @GetMapping("/asd/{name}")
    public String welcome(@PathVariable String name) {
        return name;
    }

    /**
     * 测试日志输出（测试完成）
     * @return
     */
    @RequestMapping("/login")
    public Object foo() {
        logger.info("打印日志----------------------");
        return  "login";
    }

    /**
     * 添加session，测试监听器的执行效果
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public Object index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("zxc", "zxc");
        return  "index";
    }

    /**
     * 获取监听器目前监听的人员数量
     * @return
     */
    @RequestMapping("/online")
    @ResponseBody
    public Object online() {
        return  "当前在线人数：" + MyHttpSessionListener.online + "人";
    }

}
