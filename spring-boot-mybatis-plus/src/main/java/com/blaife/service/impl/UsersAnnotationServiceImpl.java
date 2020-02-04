package com.blaife.service.impl;

import com.blaife.mapper.UsersAnnotationMapper;
import com.blaife.model.Users;
import com.blaife.service.UsersAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersAnnotationServiceImpl implements UsersAnnotationService {

    @Autowired
    @SuppressWarnings(value = "all")
    UsersAnnotationMapper users;

    @Override
    public List<Users> getUserByName(String name) {
        return users.getUserByName(name);
    }
}
