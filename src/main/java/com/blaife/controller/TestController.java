package com.blaife.controller;


import com.blaife.model.NeoProperties;
import com.blaife.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 *
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 测试helloworld
     * @return
     */
    @RequestMapping("/hello")
    public String index() {
        return "helloWorld";
    }

    /**
     * 输出实体类
     * @return
     */
    @RequestMapping("getUser")
    public User getUser() {
        User user = new User();
        user.setUserName("blaife");
        user.setPassWord("0121");
        return user;
    }


    /**
     * 测试配置文件
     * @return
     */
    @RequestMapping("/testProperties")
    public NeoProperties testProperties() {
        NeoProperties neo = new NeoProperties();
        return neo;
    }

    /**
     * 通过缓存访问 (通过缓存访问目前依旧存在问题)
     *
     * 错误如下：
     * java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable
     * payload but received an object of type [com.blaife.test.model.User]
     * @return
     */
    @RequestMapping("/getUserByCache")
    @Cacheable(value = "user-key")
    public User getUserByCache() {
        User user = new User();
        user.setUserName("blaife");
        user.setPassWord("0121");
        System.out.println("测试是否使用缓存读取");
        return user;
    }

    /**
     * 将uid存入session
     * @param session
     * @return
     */
    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

}
