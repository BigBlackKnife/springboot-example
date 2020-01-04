# 引入redis依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
springBoot2.0中的redis客户端依赖jedis或Lettuce，实际上是对jedis这些客户端的封装，
提供一套与客户端无关的api供应用使用，从而你在从一个redis客户端切换为另一个客户端，不需要修改业务代码。  

# redis基本配置信息
```properties
# redis配置
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=123456
# 连接池最大连接数（使用负值表示没有限制） 默认 8
spring.redis.lettuce.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
spring.redis.lettuce.pool.max-wait=-1
# 连接池中的最大空闲连接 默认 8
spring.redis.lettuce.pool.max-idle=8
# 连接池中的最小空闲连接 默认 0
spring.redis.lettuce.pool.min-idle=0
# 连接超时倒计时
spring.redis.timeout=5000
```
springboot2.0中redis默认的客户端为lettuce
如果要使用jedis，需要引入jedis的依赖并在配置文件中使用jedis的配置

# 序列化方式配置
使用RedisTemplate或者StringRedisTemplate。redisTemplate需要进行序列化设置,  默认配置的jdk序列化会导致在客户端查看不了数据 (仍可使用内在函数存取修改, 只是查看不了), 
为避免这种情况发生, 使用StringRedisTemplate或自行配置序列化。
```java
package com.blaife.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * Redis配置类
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 自定义redis模板 <String, Object>
     * redisTemplate使用默认的jdk序列化会导致客户端查看不了数据
     * 使用StringRedisTemplate并自行配置序列化
     * @param factory
     * @return
     */
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}

```
如上代码，修改RedisTemplate的序列化方式。

# redis工具类
目前有很多开源的redis工具类，本项目工具类为RedisUtil。  
需要注意的是本工具类需要将其设置为Bean,即在类上加入注解@Component.  
在其下方RedisTemplate及StringRedisTemplate上加入注解@Autowired

# 测试redis是否可以正常使用
详细可见TestRedis测试类
进行简单的存储和提取测试
```java
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
```
至此，一个基本的Springboot使用redis的demo就算完成了