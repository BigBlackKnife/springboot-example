package com.blaife.controller;

import com.blaife.model.Users;
import com.blaife.service.UsersAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/annotation")
public class UserAnnotationController {

    @Autowired
    UsersAnnotationService user;

    /**
     * 通过用户名获取用户信息
     * @param name
     * @return
     */
    @RequestMapping("/getUserByName")
    public List<Users> getUserByName(String name) {
        return user.getUserByName(name);
    }

}
