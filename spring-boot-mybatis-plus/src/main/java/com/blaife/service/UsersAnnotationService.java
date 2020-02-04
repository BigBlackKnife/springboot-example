package com.blaife.service;

import com.blaife.model.Users;

import java.util.List;

public interface UsersAnnotationService {

    /**
     * 根据用户名获取用户信息
     * @return
     */
    List<Users> getUserByName(String name);

}
