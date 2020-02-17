package com.blaife;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LogTest {

    // 创建日志对象
    private Logger log = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void outputLog() {
        log.trace("这是 trace");
        log.debug("这是 debug");
        log.info("这是 info");
        log.warn("这是 warn");
        log.error("这是 error");
    }

}
