package com.blaife.filter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * session过滤器
 */
@RestController
@RequestMapping("/sessionTest")
public class SessionController {

    /**
     * 跳转到登陆页面（在session拦截器拦截后，跳转的位置）
     * @return
     */
    @RequestMapping("/loginHtml")
    public String loginHtml() {
        return "this is loginHtml";
    }


    /**
     * 登录方法 （简单点） 执行则直接登陆
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpSession session) {
        session.setAttribute("user","blaife");
        return "blaife is login";
    }

    @RequestMapping("/main")
    public String testMain() {
        return "is main";
    }
}
