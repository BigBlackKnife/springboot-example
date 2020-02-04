package com.blaife.mapper;

import com.blaife.model.Users;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 第二种：注解形式
 */
public interface UsersAnnotationMapper {

    /**
     * 根据用户名查询用户信息
     * @return
     */
    @Select("SELECT id, `name`, age FROM users WHERE `name` = #{name}")
    List<Users> getUserByName(String name);


}
