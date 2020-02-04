package com.blaife.mapper;

import com.blaife.model.Users;

import java.util.List;

public interface UsersXmlMapper {

    /**
     * 插入多条语句，印证动态sql foreach
     * @param list
     * @return
     */
    int insertUserMultiterm(List<Users> list);

}
