# Redis实现数据缓存
redis启动后数据都存在内存中，读写效率比较高，在一些不经常改动，但又经常查询的数据是最适合使用缓存的。

## 添加依赖
### 数据池安全依赖
```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
```
### redis依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
### json格式工具包
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.15</version>
</dependency>
```
此工具包可以使用其他方式代替，在此案例中它的作用是重新设置redis的序列化方式。

## 添加redis配置信息
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

### redis序列化方式修改类
```java
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * 重新设置redis数据序列化方式
 * @param <T>
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    // Charset: 字符集
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T)JSON.parseObject(str, clazz);
    }
}
```

## redis配置类
```java
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * redis配置
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

    /**
     * 设置 redis 数据默认过期时间
     * 设置 Cache 序列化方式
     * @return
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
                .entryTtl(Duration.ofSeconds(60));
        return configuration;
    }

    /**
     * 自定义生成key的规则
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                //格式化缓存key字符串
                StringBuilder sb = new StringBuilder();
                //追加类名
                sb.append(o.getClass().getName()).append(".");
                //追加方法名
                sb.append(method.getName());
                //遍历参数并且追加
                for (Object obj : objects) {
                    sb.append(".");
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
```
redis配置类中的这两个方法非常的重要，属于是redis作为缓存的核心内容，其中的配置可以根据自己的需求自由更改。

## 测试方法
```java
@Service
// 写在类上则表示此类中所有缓存的内容都使用此缓存名，可以在方法上重新配置进行覆盖
@CacheConfig(cacheNames = "StringService")
public class StringService {

    /**
     * CachePut 注解测试
     * @param data
     * @return
     */
    // @Transactional 执行数据库操做时最好添加事务注解，以免数据库与缓存数据不一致
    @CachePut(key = "#data")
    public String testPut(String data) {
        return data;
    }

    /**
     * Caching 注解测试 （put）
     * @param datas
     * @return
     */
    @Caching(put = {
            @CachePut(key = "#datas.get(0)"),
            @CachePut(key = "#datas.get(1)")
    })
    public List<String> testPuts(List<String> datas) {
        return datas;
    }

    /**
     * CacheEvict注解测试
     * @param data
     * @return
     */
    // @Transactional 执行数据库操做时最好添加事务注解，以免数据库与缓存数据不一致
    @CacheEvict(key = "#data")
    public String testEvict(String data){
        return data;
    }

    /**
     * Caching 注解测试 （evict）
     * @param datas
     * @return
     */
    @Caching(evict = {
            @CacheEvict(key = "#datas.get(0)"),
            @CacheEvict(key = "#datas.get(1)"),
    })
    public List<String> testEvicts(List<String> datas) {
        return datas;
    }

    /**
     * 根据id查询数据
     * @param data
     * @return
     */
    @Cacheable(key = "#data")
    public String testAble(String data){
        System.out.println("测试是否使用缓存");
        return "able";
    }
}
```

## 测试
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    @Autowired
    StringService stringService;

    /**
     * 测试数据修改
     */
    @Test
    public void testPut() {
        String result = stringService.testPut("blaife");
        System.out.println(result);
    }

    /**
     * 测试数据删除
     */
    @Test
    public void testEvict() {
        String result = stringService.testEvict("blaife");
        System.out.println(result);
    }

    /**
     * 测试数据缓存
     */
    @Test
    public void testAble() {
        String result = stringService.testAble("blaife");
        System.out.println(result);
    }

    @Test
    public void testPuts() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        List<String> results = stringService.testPuts(list);
        System.out.println(results);
    }

    @Test
    public void testEvicts() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        List<String> results = stringService.testEvicts(list);
        System.out.println(results);
    }
}
```

## 注解解释
### @CacheConfig
这个注解主要用于配置该类中会用到的一些公用的缓存配置。我们也可以不使用该注解，直接通过自己的注解配置缓存集的名字来定义。
再方法上重新定义后会覆盖类上配置的名称
```java
@CacheConfig(cacheNames = "StringService")
public class StringService {}
```

### 常用redis注解CachePut,Cacheable,CacheEvict。
公共内容，注解参数。
- value : 缓存名，必须至少指定一个（类上配置了，方法中可不配置，方法上配置后会覆盖类上配置的公共名称）
- key : 缓存的key，可以为空，如果指定要按照 SpEL（Spring Expression Language） 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合。
- condition : 缓存条件，可以为空，使用 SpEL 编写，返回true或者false，只有为true才进行缓存。

#### @CachePut
此注解直接将返回值更新到redis缓存中，用于添加和修改方法  
```java
@CachePut(value = "String", key = "#data", condition = "#s != blaife") 
public String setString(String data) {
    return data;
}
```

#### @Cacheable
此注解在执行前查看reids缓存中是否存在，如果存在则之间返回缓存内容，不存在则执行方法并将返回值存入缓存
```java
@CachePut(value = "String", key = "#data", condition = "#s != blaife") 
public String getString(String data) {
    return data;
}
```

#### @CacheEvict
此注解在执行方法成功后会从缓存中移除相应值
- allEntries : 是否清空所有缓存内容，缺省为false如果指定为true则方法调用后将立即清空所有缓存.(慎用)
- beforeInvocation : 是否在方法执行前就清空，缺省为false如果指定为true则在方法还没有执行的时候就清空缓存，(缺省情况下，如果方法执行抛出异常，则不会清空缓存。)
```java
@CacheEvict(value = "String", key = "#data",allEntries = false, beforeInvocation = false, condition = "#result.username ne 'zhang'") 
public String deleteString(String data){
    return data;
}
```

### @Caching
这个注解组合多个Cache注解使用，并且可以自定义注解使用。
#### 组合使用
```java
/**
 * Caching 注解测试 （evict）
 * @param datas
 * @return
 */
@Caching(evict = {
        @CacheEvict(key = "#datas.get(0)"),
        @CacheEvict(key = "#datas.get(1)"),
})
public List<String> testEvicts(List<String> datas) {
    return datas;
}
```

#### 自定义注解使用
自定义注解类
```java
@Caching(
        put = {
                @CachePut(key = "#datas.get(0)"),
                @CachePut(key = "#datas.get(1)")
        }
)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MyCache {}
```
测试方法
```java
@MyCache
public List<String> testPuts(List<String> datas) {
    return datas;
}
```

## SpEL（Spring Expression Language） spring表达式语言
SPEL语言是一个非常强力的支持运行时查询和操作对象图谱的语言.这个语言语法和传统EL表达式相似,但提供了许多额外功能,最重要的是函数调用和基本字符串模板函数;  
具体使用请查阅相关资料。
