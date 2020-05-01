package com.blaife.controller;

import com.blaife.model.User;
import com.blaife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Blaife
 * @description user controller层
 * @data 2020/4/30 16:44
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findUserByName")
    public User findUserByName() {
        return userService.findUserByName("blaife");
    }
}
