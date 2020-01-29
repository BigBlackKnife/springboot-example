package com.blaife.interceptor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @RequestMapping("/testInterception")
    public void testInterception() {
        System.out.println("test to testInterception on controller");
    }

}
