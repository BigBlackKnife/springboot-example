package com.blaife.service.impl;

import com.blaife.mapper.UsersXmlMapper;
import com.blaife.model.Users;
import com.blaife.service.UsersXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersXmlServiceImpl implements UsersXmlService {

    @Autowired
    @SuppressWarnings(value = "all")
    UsersXmlMapper users;

    @Override
    public int insertUserMultiterm() {
        List<Users> list = new ArrayList<>();
        Users u1 = new Users();
        u1.setId(UUID.randomUUID().toString().replace("-", ""));
        u1.setName("Multiterm One");
        u1.setAge(1);
        list.add(u1);
        Users u2 = new Users();
        u2.setId(UUID.randomUUID().toString().replace("-", ""));
        u2.setName("Multiterm One");
        u2.setAge(2);
        list.add(u2);
        return users.insertUserMultiterm(list);
    }
}
