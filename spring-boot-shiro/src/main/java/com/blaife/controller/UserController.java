package com.blaife.controller;

import com.blaife.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Blaife
 * @description user Controller类
 * @data 2020/4/24 23:59
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/toUnAuth")
    public String toUnAuth() {
        return "user/unAuth";
    }

    @RequestMapping("/login")
    public String login(String name, String password, Model model) {
        // 使用shiro编写认证操作
        // 1. 获取subject
        Subject subject = SecurityUtils.getSubject();
        // 2. 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        // 3. 执行登录操作
        try {
            subject.login(token);
            return "redirect:/user/toIndex";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @RequestMapping("/toIndex")
    public String toIndex(Model model) {
        model.addAttribute("name",  "blaife");
        return "user/index";
    }


    @RequestMapping("/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/update")
    public String update() {
        return "user/update";
    }
}
