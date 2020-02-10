package com.blaife.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blaife.model.Users;

import java.util.List;

public interface UsersXmlMapper {

    /**
     * 插入多条语句，印证动态sql foreach
     * @param list
     * @return
     */
    int insertUserMultiterm(List<Users> list);

    /**
     * 分页查询用户列表
     * @param page
     * @return
     */
    IPage<Users> selectPageUses(Page<?> page);

}
