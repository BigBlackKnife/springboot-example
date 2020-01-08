package com.blaife.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 生产者
 */
@Component
public class HelloSender4 {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String i) {
        String context = "hello " + i + " " + new Date();
        System.out.println("Sender4 : " + context);
        this.rabbitTemplate.convertAndSend("hello2", context);
    }

}
