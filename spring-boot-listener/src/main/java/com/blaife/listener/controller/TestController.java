package com.blaife.listener.controller;

import com.blaife.listener.model.User;
import com.blaife.listener.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class TestController {

    @Autowired
    UserService userService;

    /**
     * 测试自定义监听器的使用
     * @return
     */
    @RequestMapping("/getUserTestCustomerListener")
    public User getUserTestCustomerListener() {
        return userService.getUserTestCustomerLintener();
    }

    /**
     * 添加用户blaife
     * @return
     */
    @RequestMapping("/addSessionBlaife")
    public String addSessionBlaife(HttpSession session) {
        session.setAttribute("UserBlaife", "blaife");
        return "用户blaife登陆";
    }

    /**
     * 移除用户blaife
     * @return
     */
    @RequestMapping("/removeSessionBlaife")
    public String removeSessionBlaife(HttpSession session) {
        session.removeAttribute("UserBlaife");
        return "用户blaife注销";
    }

    /**
     * 添加用户blaife
     * @return
     */
    @RequestMapping("/addSessionConner")
    public String addSessionConner(HttpSession session) {
        session.setAttribute("UserConner", "conner");
        return "用户Conner登陆";
    }

    /**
     * 移除用户blaife
     * @return
     */
    @RequestMapping("/removeSessionConner")
    public String removeSessionConner(HttpSession session) {
        session.removeAttribute("UserConner");
        return "用户conner注销";
    }

    /**
     * 销毁当前session会话信息
     * @param session
     * @return
     */
    @RequestMapping("/sessionInvaildate")
    public String sessionInvaildate(HttpSession session) {
        session.invalidate();
        return "销毁session会话";
    }
}
