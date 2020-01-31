package com.blaife.listener.listener;

import com.blaife.listener.event.MyCustomerEvent;
import com.blaife.listener.model.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 自定义监听器，监听myCustomerEvent事件
 */
@Component
public class MyCustomEventListener implements ApplicationListener<MyCustomerEvent> {
    @Override
    public void onApplicationEvent(MyCustomerEvent myCustomerEvent) {
        // 把事件中的信息获取到
        User user = myCustomerEvent.getUser();
        // 处理事件，实际项目中可以通知别的微服务或者处理其他逻辑等
        System.out.println("用户名：" + user.getName());
        System.out.println("年龄：" + user.getAge());
        System.out.println("密码：" + user.getPwd());
    }
}
