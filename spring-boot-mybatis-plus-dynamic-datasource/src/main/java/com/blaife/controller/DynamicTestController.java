package com.blaife.controller;

import com.blaife.service.DynamicTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DynamicTestController {

    @Autowired
    DynamicTestService service;

    @RequestMapping("/getMessage")
    public List<String> getMessage() {
        return service.getMessage();
    }

}
