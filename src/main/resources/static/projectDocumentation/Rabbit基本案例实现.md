# 引入rabbit依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

# rabbit配置文件
```java
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
```
上面的配置文件配置了3个队列`hello`、`hello2`、`model`3个.

# 书写发送者
```java
package com.blaife.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 生产者
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

}
```
发送者的convertAndSend方法中第一个参数是队列名称，此队列名称必须是配置文件中已经配置好的。

# 书写接收者
```java
package com.blaife.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收者
 */
@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }
}
```
接收者文件中的@RabbitListener注解需要使用对应生产者发送时的队列。

# 测试文件
```java
package com.blaife;

import com.blaife.rabbitmq.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqHelloTest {

    @Autowired
    private HelloSender helloSender;

    @Autowired
    private HelloSender2 helloSender2;

    @Autowired
    private HelloSender3 helloSender3;

    @Autowired
    private HelloSender4 helloSender4;

    @Autowired
    private HelloSender5 helloSender5;

    /**
     * 1对1发送 单条数据测试
     * @throws Exception
     */
    @Test
    public void hello() throws Exception {
        helloSender.send();
    }

    /**
     * 1对多发送 多条数据测试
     * @throws Exception
     */
    @Test
    public void helloi() throws Exception {
        for (int i = 0; i < 100; i++) {
            helloSender2.send(i);
        }
    }

    /**
     * 多对一发送 两条数据测试
     */
    @Test
    public void hello2() {
        helloSender3.send("one");
        helloSender4.send("two");
    }

    /**
     * 多对一发送 两条数据测试
     */
    @Test
    public void helloModel() {
        helloSender5.send();
    }
}
```
需要注意的是发送的如果是model对象，需要model对象实现序列化，不要发送时对提示对象序列化失败等信息