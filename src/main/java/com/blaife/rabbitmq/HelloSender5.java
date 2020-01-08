package com.blaife.rabbitmq;

import com.blaife.model.LomBokTest;
import lombok.Lombok;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 生产者
 */
@Component
public class HelloSender5 {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        LomBokTest model = new LomBokTest();
        model.setName("blaife");
        model.setAge("23");
        model.setPwd("blaife");
        System.out.println("Sender5 : " + model);
        this.rabbitTemplate.convertAndSend("model", model);
    }

}
