package com.blaife.filter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试过滤器中order是否可用
 */
@RestController
public class OrderController {

    @RequestMapping("testOrder")
    public void testOrder() {}
}
