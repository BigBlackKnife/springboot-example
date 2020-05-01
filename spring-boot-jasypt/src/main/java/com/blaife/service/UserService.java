package com.blaife.service;

import com.blaife.model.User;

/**
 * @author Blaife
 * @description user service层接口
 * @data 2020/4/30 14:56
 */
public interface UserService {
    /**
     * 通过用户名查找用户
     * @param name 用户名
     * @return user实体
     */
    public User findUserByName(String name);
}
