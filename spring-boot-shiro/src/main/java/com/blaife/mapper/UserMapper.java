package com.blaife.mapper;

import com.blaife.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 功能描述: 用户mapper层
 * @Author: Blaife
 * @Date: 2020/4/25 21:50
 */
@Mapper
public interface UserMapper {

    /**
     * 通过用户名获取用户信息
     * @param name 用户名
     * @return 用户实体
     */
    @Select("SELECT id, name, password, perms FROM users WHERE `name` = #{name}")
    public User findUserByName(String name);

}
