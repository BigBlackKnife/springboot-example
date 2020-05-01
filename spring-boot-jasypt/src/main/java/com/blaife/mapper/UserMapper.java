package com.blaife.mapper;

import com.blaife.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Blaife
 * @description user mapper层接口
 * @data 2020/4/30 14:56
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
