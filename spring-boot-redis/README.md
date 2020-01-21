# Redis 基本操作
> Redis（全称：Remote Dictionary Server 远程字典服务）是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。    

springboot项目中使用redis非常的简单，引入依赖，配置redis数据库就可以了。  

## 引入依赖包
```xml
<dependencys>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencys>
```
- 引入spring-boot-web是由于在redis模板配置中需要使用到相关类。

## 添加redis配置
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

## redis配置类
```java
@Configuration
public class RedisConfig {
    /**
     * 自定义redis模板 <Object, Object>
     * redisTemplate使用默认的jdk序列化会导致客户端查看不了数据
     * 使用StringRedisTemplate并自行配置序列化
     * @param factory
     * @return
     */
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        // 配置连接工厂        
        template.setConnectionFactory(factory);
        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 注意 此部分需要spring-boot-web依赖
        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
```
此配置文件重新配置了redisTemplate序列化规则，是我们在redis中查看数据时更加方便。

## redis工具类
工具了内容详见[RedisUtil](https://github.com/BigBlackKnife/spring-boot-examples/blob/master/spring-boot-redis/src/main/java/com/blaife/redis/utils/RedisUtil.java)

工具类中包含了对String、Hash、Set、List以及公共部分（如指定缓存失效时间等）的操作方法。

## 测试类
```java
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
```
是一个极为简单的例子，其他方法的测试就不贴出来了。