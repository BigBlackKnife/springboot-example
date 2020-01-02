package com.blaife.mapper;

import com.blaife.model.User;
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
            @Result(property = "userName",  column = "userName"),
            @Result(property = "passWord", column = "passWord")
    })
    List<User> getAll();



}
