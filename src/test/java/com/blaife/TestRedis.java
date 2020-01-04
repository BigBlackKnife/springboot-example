package com.blaife;

import com.blaife.utils.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {
    @Autowired
    RedisUtil redisUtil;

    /**
     * 简单的测试redis String 类型数据的存储和读取
     */
    @Test
    public void testSetKey() {
        redisUtil.set("ss", "sda");
        System.out.println(redisUtil.get("ss"));
    }
}
