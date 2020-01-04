# 引入依赖
```xml
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

# 添加session共享配置文件
```java
package com.blaife.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * session共享配置类
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {}
```
如此就设置了存储的session保留时间为30天

# 测试使用
```java
/**
 * 将uid存入session
 * @param session
 * @return
 */
@RequestMapping("/uid")
String uid(HttpSession session) {
    UUID uid = (UUID) session.getAttribute("uid");
    if (uid == null) {
        uid = UUID.randomUUID();
    }
    session.setAttribute("uid", uid);
    return session.getId();
}
```
执行一次之后打开redis就会发现多出了三条数据格式为：  
1.spring:session:sessions:expires:97b4e4bd-6883-4eee-acba-4d1adcaa2c5b
2.spring:session:expirations:1580725500000
3.spring:session:sessions:788794e1-39e7-4086-9fc5-93bc53d5f85f

此时，便解决了对分布式或集群的session共享的问题