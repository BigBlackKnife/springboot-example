# 修改redis配置文件如下
```java
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
/**
 * Redis配置类
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * redis缓存配置方法
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
```
主要是两点
- 1.添加@EnableCaching,此注释是开启缓存的
- 2.keyGenerator方法设置缓存内容格式的

# 基本操作
- 1.简单的基本类型String、int 等
在Controller中写入方法：如下代码
```java
/**
 * 测试redis缓存 简单的字符串
 * @return
 */
@RequestMapping("/getStr")
@Cacheable(value = "user-key")
public String getStr() {
    String str = "getStr";
    System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
    return str;
}
```
执行第一次之后，打开redis会发现存储了一些内容。
第二次再执行时，输出的内容就不会出现了，说明数据缓存成功

- 2.对于实体类对象的缓存  
这里需要注意的一点是***在实体类对象上实现序列化接口：Serializable***
如果没有继承，会提示数据构建失败`java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable`
controller中的代码如下
```java
/**
 * 测试redis缓存 实体类对象
 * @return
 */
@RequestMapping("/getUser")
@Cacheable(value = "user-key")
public RedisCacheUser getUser() {
    RedisCacheUser user = new RedisCacheUser("blaife","123456");
    System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
    return user;
}
```