package com.blaife.rabbitmq.receiver;

import com.blaife.rabbitmq.config.RabbitConfig;
import com.blaife.rabbitmq.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {

    // slf4j 日志
    private static final Logger log = LoggerFactory.getLogger(RabbitReceiver.class);

    // ============================Direct Pattern===================================

    @RabbitListener(queues = RabbitConfig.DIRECT_QUEUE)
    public void receiveDirectOne(Model msg) {
        log.info("this is direct1 receive: " + msg);
    }

    @RabbitListener(queues = RabbitConfig.DIRECT_QUEUE)
    public void receiveDirectTwo(Model msg) {
        log.info("this is direct2 receive: " + msg);
    }

    // ============================Fanout Pattern===================================

    @RabbitListener(queues = RabbitConfig.FANOUT_QUEUE_ONE)
    public void receiveFanoutOne(String msg) {
        log.info("this is fanout1 receive: " + msg);
    }

    @RabbitListener(queues = RabbitConfig.FANOUT_QUEUE_TWO)
    public void receiveFanoutTwo(String msg) {
        log.info("this is fanout2 receive: " + msg);
    }

    // ============================Topic Pattern===================================

    @RabbitListener(queues = RabbitConfig.TOPIC_QUEUE_ONE)
    public void receiveTopicOne(String msg) {
        log.info("this is topic1 receive: " + msg);
    }

    @RabbitListener(queues = RabbitConfig.TOPIC_QUEUE_TWO)
    public void receiveTopicTwo(String msg) {
        log.info("this is topic2 receive: " + msg);
    }

    // ============================Header Pattern===================================
    @RabbitListener(queues = RabbitConfig.HEADER_QUEUE)
    public void receiveHeader(String msg) {
        log.info("this is header receive: " + msg);
    }
}
