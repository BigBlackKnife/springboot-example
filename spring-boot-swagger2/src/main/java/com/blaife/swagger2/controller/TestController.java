package com.blaife.swagger2.controller;

import com.blaife.swagger2.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试Swagger2文档显示")
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "获取一个测试字符串") // 标记一个方法的作用
    @GetMapping("/getString")
    public String getString() {
        return "获取一个测试字符串";
    }

    @ApiOperation(value = "输出User信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "用户名", defaultValue = "blaife", required = true),
        @ApiImplicitParam(name = "pwd", value = "用户密码", defaultValue = "0121", required = true)
    })
    @PostMapping("/showUser")
    public User showUser(String name, String pwd) {
        User user = new User();
        user.setName(name);
        user.setPwd(pwd);
        return user;
    }

    @ApiOperation(value = "获取user对象")
    @PostMapping("/getUser")
    public User getUser(User user) {
        return user;
    }

}
