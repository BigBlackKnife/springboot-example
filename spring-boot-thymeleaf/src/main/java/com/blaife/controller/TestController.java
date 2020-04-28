package com.blaife.controller;

import com.blaife.model.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Blaife
 * @description 测试接口
 * @data 2020/4/28 16:04
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    public String test(Model model) {
        String test = "接口test";
        model.addAttribute("txt", test);
        return "test";
    }

    @RequestMapping("/myTest")
    public String myTest(Model model) {
        User user = new User("马国栋","blaife", 1, "23", "1997-01-21");
        model.addAttribute("user", user);
        model.addAttribute("mothodTest", "疏枝横玉瘦，小萼点珠光");

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        model.addAttribute("users", userList);
        return "myTest";
    }

}
