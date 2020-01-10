package com.blaife;

import com.blaife.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringBootRedis {

    @Autowired
    private RedisUtil redis;

    /**
     * 测试String类型数据的存和取
     */
    @Test
    public void testStringAccess() {
        redis.set("StringTest", "blaife");
        System.out.println(redis.get("StringTest"));
    }
}
