package com.blaife.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;

@DS("two")
public interface TwoMapper {
    String selectMessage();
}
