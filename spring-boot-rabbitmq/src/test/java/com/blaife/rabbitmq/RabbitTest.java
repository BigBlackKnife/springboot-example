package com.blaife.rabbitmq;


import com.blaife.rabbitmq.model.Model;
import com.blaife.rabbitmq.sender.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTest {

    @Autowired
    private RabbitSender sender;

    // ============================Direct Pattern===================================

    /**
     * direct 测试 多对多 发送(并且发送对象信息)
     * 发送至队列，多个消费者接收，但只能消费一次
     */
    @Test
    public void directTest() {
        sender.sendDirectOne(new Model().setName("测试direct方法").setSynopsis("direct发送方法1"));
        sender.sendDirectTwo(new Model().setName("测试direct方法").setSynopsis("direct发送方法2"));
    }

    // ============================Fanout Pattern===================================

    /**
     * fanout 测试发送5条 消费者共收到10条 （匹配到两个队列）
     */
    @Test
    public void fanoutTest() {
        for (int i = 0; i < 5; i++) {
            sender.sendFanout("test fanout for " + i);
        }
    }

    // ============================Topic Pattern===================================

    /**
     * topic 测试匹配效果
     * topic.one --> topic.# , topic.one
     * topic.two --> topic.two
      */
    @Test
    public void topicTest() {
        sender.sendTopicOne("test topic exchange one");
        sender.sendTopicTwo("test topic exchange two");
    }

    // ============================Header Pattern===================================

    /**
     * header 测试
     * 包含两种匹配模式 any, all
     */
    @Test
    public void headerTest() {
        sender.sendHeader("test header exchange");
    }
}
