package com.blaife.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 */
@Component
@RabbitListener(queues = "hello2")
public class HelloReceiver3 {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver3  : " + hello);
    }

}
