package com.blaife.redis;

import com.blaife.redis.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redis;

    /**
     * 测试String类型数据的存取
     */
    @Test
    public void testStringAccess() {
        redis.set("SpringBootTest", "Test1");
        System.out.println(redis.get("SpringBootTest"));
    }

}
