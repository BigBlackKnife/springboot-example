package com.blaife.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq 消息队列配置类
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("hello");
    }

    @Bean
    public Queue queue2() {
        return new Queue("hello2");
    }

    @Bean
    public Queue queueModel() {
        return new Queue("model");
    }

}
