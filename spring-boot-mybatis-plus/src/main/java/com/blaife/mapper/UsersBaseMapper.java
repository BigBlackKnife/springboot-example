package com.blaife.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blaife.model.Users;

/**
 * 第一种，使用BaseMapper，查询数据基于实体类，继承后自动还有一些基本的增删改查方法
 */
public interface UsersBaseMapper extends BaseMapper<Users> {}