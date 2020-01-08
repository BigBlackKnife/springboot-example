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
