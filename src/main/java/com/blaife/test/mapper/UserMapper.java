package com.blaife.test.mapper;

import com.blaife.test.model.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * User Mapper层
 */
public interface UserMapper {

    /**
     * 获取所有User数据
     * @return
     */
    @Select("SELECT * FROM users")
    @Results({
            @Result(property = "userName",  column = "name"),
            @Result(property = "passWord", column = "password")
    })
    List<User> getAll();



}
