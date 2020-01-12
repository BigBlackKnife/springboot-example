package com.blaife.redis.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class RedisTestController {

    /**
     * 测试redis缓存请求
     * @return
     */
    @RequestMapping("/testRedisCaching")
    @Cacheable(value = "user-key")
    public String testRedisCaching() {
        System.out.println("测试缓存（输出则表示未使用缓存）");
        return "testRedisCaching";
    }

    /**
     * 测试Redis集中管理session
     * 测试时可以启动两个端口两个服务，都执行这一接口，若返回相同值，则表示配置成功
     * @param session
     * @return
     */
    @RequestMapping("/testRedisSession")
    public UUID testRedisSession(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return uid;
    }

}
