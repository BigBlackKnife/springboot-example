package com.blaife.rabbitmq;

import com.blaife.model.LomBokTest;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 */
@Component
@RabbitListener(queues = "model")
public class HelloReceiver4 {

    @RabbitHandler
    public void process(LomBokTest model) {
        System.out.println("Receiver4  : " + model);
    }

}
