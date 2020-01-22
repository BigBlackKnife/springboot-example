package com.blaife.rcache.service;

import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@CacheConfig(cacheNames = "StringService")
public class StringService {

    /**
     * CachePut 注解测试
     * @param data
     * @return
     */
    // @Transactional 执行数据库操做时最好添加事务注解，以免数据库与缓存数据不一致
    @CachePut(key = "#data")
    public String testPut(String data) {
        return data;
    }

    /**
     * Caching 注解测试 （put）
     * @param datas
     * @return
     */
    @Caching(put = {
            @CachePut(key = "#datas.get(0)"),
            @CachePut(key = "#datas.get(1)")
    })
    public List<String> testPuts(List<String> datas) {
        return datas;
    }

    /**
     * CacheEvict注解测试
     * @param data
     * @return
     */
    // @Transactional 执行数据库操做时最好添加事务注解，以免数据库与缓存数据不一致
    @CacheEvict(key = "#data")
    public String testEvict(String data){
        return data;
    }

    /**
     * Caching 注解测试 （evict）
     * @param datas
     * @return
     */
    @Caching(evict = {
            @CacheEvict(key = "#datas.get(0)"),
            @CacheEvict(key = "#datas.get(1)"),
    })
    public List<String> testEvicts(List<String> datas) {
        return datas;
    }

    /**
     * 根据id查询数据
     * @param data
     * @return
     */
    @Cacheable(key = "#data")
    public String testAble(String data){
        System.out.println("测试是否使用缓存");
        return "able";
    }
}
