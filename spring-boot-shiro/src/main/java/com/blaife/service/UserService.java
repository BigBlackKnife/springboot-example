package com.blaife.service;

import com.blaife.model.User;

/**
 * @author Blaife
 * @description user - Service
 * @data 2020/4/25 22:21
 */
public interface UserService {

    /**
     * 通过用户名查找用户
     * @param name 用户名
     * @return user实体
     */
    public User findUserByName(String name);

}
