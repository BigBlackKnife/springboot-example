package com.blaife.filter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 简单的测试过滤器
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 测试过滤器
     */
    @RequestMapping("/testFilter")
    public void testFilter() {
        System.out.println("我是控制类里面的方法，我正在思考...............");
    }
}
