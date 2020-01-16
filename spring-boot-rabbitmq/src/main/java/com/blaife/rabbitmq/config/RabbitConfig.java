package com.blaife.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbit配置文件
 * Direct Pattern： 先匹配, 再投送. 即在绑定时设定一个routing_key, 消息的routing_key匹配时, 才会被交换器投送到绑定的队列中去.
 * Fanout Pattern： 类似于广播一样，将消息发送给所有和他绑定的队列
 * Topic Pattern： 按规则转发消息 #：表示零个或多个单词。*：表示一个单词（最灵活）
 * Header Pattern： 设置 header attribute参数类型的交换机
 *
 * 一些必要的概念：
 * Queue ： 队列
 * FanoutExchange ： 交换机
 * Binding ： 绑定队列和交换机
 *
 * 一个交换机绑定多个队列，如果匹配则放入对列，是复制放入，入共5条数据，匹配2个队列，则消息总数为10
 **/
@Configuration
public class RabbitConfig {

    // ============================Direct Pattern===================================

    /**
     * 队列名 Direct Queue
     */
    public static final String DIRECT_QUEUE = "direct.queue";

    /**
     * 创建队列
     * Direct Pattern 是不需要配置交换机的
     * 只需要这个方法就够了
     * @return
     */
    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE, true);
    }

    // ============================Fanout Pattern===================================

    /**
     * 交换机名 Fanout Exchange
     */
    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    /**
     * 队列名 Fanout Queue 1
     */
    public static final String FANOUT_QUEUE_ONE = "fanout.queue.one";

    /**
     * 队列名 Fanout Queue 2
     */
    public static final String FANOUT_QUEUE_TWO = "fanout.queue.two";

    /**
     * 创建交换机
     * Fanout Exchange
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * 创建队列
     * Fanout Queue 1
     * @return
     */
    @Bean
    public Queue fanoutQueueOne() {
        return new Queue(FANOUT_QUEUE_ONE, true);
    }

    /**
     * 将队列(Fanout Queue 1)绑定到交换机(Fanout Exchange)上
     * @return
     */
    @Bean
    public Binding fanoutBindingOne() {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }

    /**
     * 创建队列
     * Fanout Queue 2
     * @return
     */
    @Bean
    public Queue fanoutQueueTwo() {
        return new Queue(FANOUT_QUEUE_TWO, true);
    }

    /**
     * 将队列(Fanout Queue 2)绑定到交换机(Fanout Exchange)上
     * @return
     */
    @Bean
    public Binding fanoutBindingTwo() {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }

    // ============================Topic Pattern===================================

    /**
     * 交换机名 Topic Exchange
     */
    public static final String TOPIC_EXCHANGE = "topic.exchange";

    /**
     * 队列名 Topic Queue 1
     */
    public static final String TOPIC_QUEUE_ONE = "topic.queue.one";

    /**
     * 队列名 Topic Queue 2
     */
    public static final String TOPIC_QUEUE_TWO = "topic.queue.two";

    /**
     * 匹配规则 Topic Key 1
     * 精确匹配
     */
    public static final String TOPIC_KEY_ONE = "topic.one";

    /**
     * 匹配规则 Topic Key 2
     * 模糊匹配
     */
    public static final String TOPIC_KEY_TWO = "topic.#";

    /**
     * 创建交换机
     * Topic Exchange
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * 创建队列
     * Topic Queue 1
     * @return
     */
    @Bean
    public Queue topicQueueOne() {
        return new Queue(TOPIC_QUEUE_ONE, true);
    }

    /**
     * 将队列(Topic Queue 1)绑定到交换机(Topic Exchange)上,并设置匹配规则（Topic Key 1）
     * @return
     */
    @Bean
    public Binding topicBindingOne() {
        return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with(TOPIC_KEY_ONE);
    }

    /**
     * 创建队列
     * Topic Queue 2
     * @return
     */
    @Bean
    public Queue topicQueueTwo() {
        return new Queue(TOPIC_QUEUE_TWO, true);
    }

    /**
     * 将队列(Topic Queue 2)绑定到交换机(Topic Exchange)上,并设置匹配规则（Topic Key 2）
     * @return
     */
    @Bean
    public Binding topicBindingTwo() {
        return BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).with(TOPIC_KEY_TWO);
    }

    // ============================Header Pattern===================================

    /**
     * 交换机名 Header Exchange
     */
    public static final String HEADER_EXCHANGE = "header.exchange";

    /**
     * 队列名 Header Queue
     */
    public static final String HEADER_QUEUE = "header.queue";

    /**
     * 创建交换机
     * Header Exchange
     * @return
     */
    @Bean
    public HeadersExchange headerExchange() {
        return new HeadersExchange(HEADER_EXCHANGE);
    }

    /**
     * 创建队列
     * Header Queue
     * @return
     */
    @Bean
    public Queue headerQueue() {
        return new Queue(HEADER_QUEUE, true);
    }

    /**
     * 将队列(Header Queue)绑定到交换机(Header Exchange)上
     * 匹配方式又 any 和 all 两种 即 || 和 && 的关系
     * @return
     */
    @Bean
    public Binding headerBinDing() {
        Map<String, Object> map = new HashMap<>();
        map.put("header1", "value1");
        map.put("header2", "value2");
        return BindingBuilder.bind(headerQueue()).to(headerExchange()).whereAll(map).match();
    }
}
