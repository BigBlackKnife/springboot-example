# Spring-Boot-Redis-Session

## pom依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

       
```
```xml
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```
## 数据源配置
```properties
# 端口号
server.port=8080

# redis配置
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=123456
```

## 有效时间配置
```java
package com.blaife.rsesson.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {}
```
在使用redis session 之后，原 Spring Boot 的 server.session.timeout 属性不再生效。

## 接口
```java
@RestController
public class HelloController {

    @Value("${server.port}")
    Integer port;

    @GetMapping("/set")
    public String set(HttpSession session) {
        session.setAttribute("user", "blaife");
        return String.valueOf(port);
    }

    @GetMapping("/get")
    public String get(HttpSession session) {
        return session.getAttribute("user") + ":" + port;
    }

}
```

## 打包
在idea的右侧Maven --> Lifecycle --> package 点击之后自动打包， 默认路径为项目的target目录下。

## 启动jar包
在jar包根目录下执行 `java -jar XXXXXXXX.jar --server.port=XXXX`

## 测试
启动两个实例（需要不同端口), 执行启动一个的set方法， 测试两个实例的get方法发现都存在数据。即测试完成。

## 注意
如果项目打包失败，可能需要在pom依赖中加上如下代码
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <skip>true</skip>
        <testFailureIgnore>true</testFailureIgnore>
    </configuration>
</plugin>
```