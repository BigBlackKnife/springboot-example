package com.blaife.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blaife.model.Users;
import com.blaife.service.UsersXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/xml")
public class UsersXmlController {

    @Autowired
    UsersXmlService users;


    @RequestMapping("/insertUserMultiterm")
    public String insertUserMultiterm() {
        int r = users.insertUserMultiterm();
        if (r > 0) {
            return "数据插入成功";
        } else {
            return "数据插入失败";
        }
    }

    @RequestMapping("/selectPageUses")
    public IPage<Users> selectPageUses() {
        return users.selectPageUses();
    }

}
