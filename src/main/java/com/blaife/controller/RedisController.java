package com.blaife.controller;

import com.blaife.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * redis的controller测试类
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    /**
     * 测试redis缓存的数据
     * @return
     */
    @RequestMapping("testCaching")
    @Cacheable(value = "user-key")
    public String getStringTestCaching() {
        System.out.println("测试缓存读取");
        return "Caching";
    }

    /**
     * 测试redis实现的session共享
     * @param session
     * @return
     */
    @RequestMapping("/testSession")
    public UUID getUidTestSession(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return uid;
    }
}
