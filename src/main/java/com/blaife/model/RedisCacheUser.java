package com.blaife.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 测试redis缓存的实体类
 */
@Data
@AllArgsConstructor
public class RedisCacheUser implements Serializable {

    private String name;
    private String pwd;



}
