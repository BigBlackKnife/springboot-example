package com.blaife.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blaife.model.Users;

import java.util.List;

public interface UsersXmlService {

    /**
     * 插入多条语句，印证动态sql foreach
     * @return
     */
    int insertUserMultiterm();

    /**
     * 分页查询用户列表
     * @return
     */
    IPage<Users> selectPageUses();

}
