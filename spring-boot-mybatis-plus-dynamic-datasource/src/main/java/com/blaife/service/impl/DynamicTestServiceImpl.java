package com.blaife.service.impl;

import com.blaife.mapper.OneMapper;
import com.blaife.mapper.TwoMapper;
import com.blaife.service.DynamicTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicTestServiceImpl implements DynamicTestService {

    @Autowired
    OneMapper one;

    @Autowired
    TwoMapper two;

    @Override
    public List<String> getMessage() {
        List<String> result = new ArrayList<>();
        result.add(one.selectMessage());
        result.add(two.selectMessage());
        return result;
    }
}
