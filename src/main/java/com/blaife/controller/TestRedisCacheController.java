package com.blaife.controller;

import com.blaife.model.RedisCacheUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试redis缓存的controller
 *
 */
@RequestMapping("/redisCache")
@RestController
public class TestRedisCacheController {

    /**
     * 测试redis缓存 简单的字符串
     * @return
     */
    @RequestMapping("/getStr")
    @Cacheable(value = "user-key")
    public String getStr() {
        String str = "getStr";
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return str;
    }

    /**
     * 测试redis缓存 实体类对象
     * @return
     */
    @RequestMapping("/getUser")
    @Cacheable(value = "user-key")
    public RedisCacheUser getUser() {
        RedisCacheUser user = new RedisCacheUser("blaife","123456");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }
}
