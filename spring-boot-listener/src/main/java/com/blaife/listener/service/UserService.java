package com.blaife.listener.service;

import com.blaife.listener.event.MyCustomerEvent;
import com.blaife.listener.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 模拟从数据库中获取数据
     * @return
     */
    public User getUser() {
        System.out.println("this is getUser Method");
        return new User("blaife", Math.random() + " ", "blaife01");
    }


    /**
     * 测试自定义监听器的使用效果
     * @return
     */
    public User getUserTestCustomerLintener() {
        User user = new User("blaife", Math.random() + " ", "blaife01");
        MyCustomerEvent event = new MyCustomerEvent(this, user);
        applicationContext.publishEvent(event);
        return user;
    }

}
