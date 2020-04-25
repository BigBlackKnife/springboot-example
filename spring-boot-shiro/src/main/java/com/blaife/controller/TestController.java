package com.blaife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Blaife
 * @description 测试接口
 * @data 2020/4/24 18:42
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/testThymeleaf")
    public String testThymeleaf(Model model) {
        model.addAttribute("name",  "blaife");
        return "test";
    }

}
