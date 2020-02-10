# Dynamic Datasource
dynamic-datasource-spring-boot-starter 是一个基于springboot的快速集成多数据源的启动器。  
它的作者和MyBatis-Plus的作者是同一人，都是baomidou，具体的介绍可以在mybatis的官方网站看的。[dynamic-datasource](https://mybatis.plus/guide/dynamic-datasource.html)  
本案例基于mybatis-plus和MySql实现一个小例子。

## Maven依赖
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```
```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
    <version>2.5.4</version>
</dependency>
```
```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.3.0</version>
</dependency>
```

## 配置文件
```properties
# mybatis-plus:PO类位置
mybatis-plus.type-aliases-package=con,blaife.model
# mybatis-plus:配置文件位置
mybatis-plus.config-location=classpath:mybatis/mybatis-config.xml
# mybatis-plus:mapper映射文件位置
mybatis-plus.mapper-locations=classpath:mybatis/mapper/**/*.xml

# mySql 数据库配置
spring.datasource.dynamic.primary=one
# 数据库one
spring.datasource.dynamic.datasource.one.url=jdbc:mysql://localhost:3306/source_one?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
spring.datasource.dynamic.datasource.one.username=root
spring.datasource.dynamic.datasource.one.password=blaife
spring.datasource.dynamic.datasource.one.driver-class-name=com.mysql.cj.jdbc.Driver
# 数据库two
spring.datasource.dynamic.datasource.two.url=jdbc:mysql://localhost:3306/source_two?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
spring.datasource.dynamic.datasource.two.username=root
spring.datasource.dynamic.datasource.two.password=blaife
spring.datasource.dynamic.datasource.two.driver-class-name=com.mysql.cj.jdbc.Driver
```

## mapper层
```java
public interface OneMapper {
    String selectMessage();
}

@DS("two")
public interface TwoMapper {
    String selectMessage();
}
```

因为在配置文件中我们将one设置为默认数据库，所以在OneMapper中默认使用one数据库。早TwoMapper中使用`@DS(two)`。设置使用two数据库。  
Dynamic Datasource只有一个注解就是@DS，此注解可以配置到类上也可以配置到方法上，配置到方法上后，会覆盖到类上的配置。

其他Controller和Service的代码就不在此赘述。