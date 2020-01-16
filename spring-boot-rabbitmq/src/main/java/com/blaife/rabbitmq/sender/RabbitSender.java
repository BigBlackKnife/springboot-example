package com.blaife.rabbitmq.sender;

import com.blaife.rabbitmq.config.RabbitConfig;
import com.blaife.rabbitmq.model.Model;
import com.blaife.rabbitmq.receiver.RabbitReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RabbitSender {

    // slf4j 日志
    private static final Logger log = LoggerFactory.getLogger(RabbitSender.class);

    // Rabbit模板对象
    @Autowired
    private AmqpTemplate amqpTemplate;

    // ============================Direct Pattern===================================

    /**
     * 发送消息 Direct模式 发送者1
     * 发送至队列
     * @param msg
     */
    public void sendDirectOne(Model msg) {
        log.info("this is direct1 send: " + msg);
        amqpTemplate.convertAndSend(RabbitConfig.DIRECT_QUEUE, msg);
    }

    /**
     * 发送消息 Direct模式 发送者2
     * 发送至队列
     * @param msg
     */
    public void sendDirectTwo(Model msg) {
        log.info("this is direct2 send: " + msg);
        amqpTemplate.convertAndSend(RabbitConfig.DIRECT_QUEUE, msg);
    }

    // ============================Fanout Pattern===================================

    /**
     * 发送消息 Fanout模式
     * 发送至交换机
     * 需要注意的是，虽然不需要匹配规则，但是还需要传入一个空字符串
     * @param msg
     */
    public void sendFanout(String msg) {
        log.info("this is fanout send: " + msg);
        amqpTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE, "", msg);
    }

    // ============================Topic Pattern===================================

    /**
     * 发送消息 Topic模式
     * 发送至交换机 都可以接受到
     * @param msg
     */
    public void sendTopicOne(String msg) {
        log.info("this is topic1 send: " + msg);
        amqpTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, "topic.one", msg);
    }

    /**
     * 发送消息 Topic模式
     * 发送至交换机 仅队列2可接收到
     * @param msg
     */
    public void sendTopicTwo(String msg) {
        log.info("this is topic2 send: " + msg);
        amqpTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, "topic.two", msg);
    }

    // ============================Header Pattern===================================

    /**
     * 发送消息 Header模式
     * 发送至交换机  mp需要与配置中的map完全匹配，因为那边是whereAll()方法
     * @param msg
     */
    public void sendHeader(String msg) {
        log.info("this is header send: " + msg);
        MessageProperties mp = new MessageProperties();
        mp.setHeader("header1", "value1");
        mp.setHeader("header2", "value2");
        Message message = new Message(msg.getBytes(), mp);
        amqpTemplate.convertAndSend(RabbitConfig.HEADER_EXCHANGE, "", message);
    }

}
