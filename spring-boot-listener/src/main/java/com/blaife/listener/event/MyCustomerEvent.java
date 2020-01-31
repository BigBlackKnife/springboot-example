package com.blaife.listener.event;

import com.blaife.listener.model.User;
import org.springframework.context.ApplicationEvent;

public class MyCustomerEvent extends ApplicationEvent {

    private User user;

    public MyCustomerEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
