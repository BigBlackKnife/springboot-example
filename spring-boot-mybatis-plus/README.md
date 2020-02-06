# Spring-Boot-MyBatis-Plus
MyBatis-plus的官方网址为[https://mybatis.plus/](https://mybatis.plus/),他的优点我就不在这里赘述了。感兴趣的朋友请查阅官方网站。

## 配置信息
### 1.pom依赖
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
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.3.0</version>
</dependency>
```
使用的数据库是mysql，所以把mysql的jar包依赖也引入进来。

### 2.配置信息（properties,yml）
```properties
# mybatis-plus:PO类位置
mybatis-plus.type-aliases-package=con,blaife.model
# mybatis-plus:配置文件位置
mybatis-plus.config-location=classpath:mybatis/mybatis-config.xml
# mybatis-plus:mapper映射文件位置
mybatis-plus.mapper-locations=classpath:mybatis/mapper/**/*.xml

# mySql 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/mater?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=blaife
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 3.配置文件(mybatis-config)
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <settings>
        <!--配置sql调试-->
        <setting name="logImpl" value="STDOUT_LOGGING" />
    </settings>

    <!--配置java与mySql中的数据类型对比-->
    <typeAliases>
        <typeAlias alias="Integer" type="java.lang.Integer" />
        <typeAlias alias="Long" type="java.lang.Long" />
        <typeAlias alias="HashMap" type="java.util.HashMap" />
        <typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
        <typeAlias alias="ArrayList" type="java.util.ArrayList" />
        <typeAlias alias="LinkedList" type="java.util.LinkedList" />
    </typeAliases>
</configuration>
```

## 基本操作流程
启动类中可以添加注解`@MapperScan("包名")`，指向mapper包，这样就不需要在每个mapper层添加`@Mapper`注解了。  

实体类中需要引入myBatis-plus的注解，关于注解的具体说明，请访问官方文档[https://mybatis.plus/guide/annotation.html](https://mybatis.plus/guide/annotation.html)
```java
package com.blaife.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * users实体类
 */
@TableName("users")
public class Users {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "age")
    private Integer age;

    // get,set...
}
```
以下这三种方式可以结合使用互不影响

### 1.BeseMapper自动生成方法方法
#### Mapper层
```java
package com.blaife.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blaife.model.Users;

/**
 * 第一种，使用BaseMapper，查询数据基于实体类，继承后自动还有一些基本的增删改查方法
 */
public interface UsersBaseMapper extends BaseMapper<Users> {}
```
BaseMapper中实现了一些简单的增删改查的方法，可以实现简单的业务。

#### Controller层
```java
package com.blaife.controller;

import com.blaife.mapper.UsersBaseMapper;
import com.blaife.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/baseMapper")
public class UsersBaseMapperController {

    @Autowired
    @SuppressWarnings(value = "all")
    private UsersBaseMapper user;

    /**
     * 单条数据添加
     * @param name
     * @param age
     * @return
     */
    @RequestMapping("/insert")
    public String insert(String name, Integer age) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Users usersModel = new Users();
        usersModel.setId(uuid);
        usersModel.setName(name);
        usersModel.setAge(age);
        int r = user.insert(usersModel);
        if (r > 0) {
            return "添加用户"+ name +"成功，uuid为" + uuid;
        } else {
            return "添加失败";
        }
    }

    /**
     * 根据id删除用户
     * @param uuid
     * @return
     */
    @RequestMapping("/deleteByUUID")
    public String deleteByUUID(String uuid) {
        int r = user.deleteById(uuid);
        if (r > 0) {
            return "删除用户成功，uuid为" + uuid;
        } else {
            return "删除失败";
        }
    }

    /**
     * 根据用户Id修改数据
     * @return
     */
    @RequestMapping("/updateByUUID")
    public String updateByUUID(String uuid) {
        Users usersModel = new Users();
        usersModel.setId(uuid);
        usersModel.setName("lisex");
        usersModel.setAge(18);
        int r = user.updateById(usersModel);
        if (r > 0) {
            return "修改用户信息成功，uuid为" + uuid;
        } else {
            return "修改失败";
        }
    }

    /**
     * 获取users表中的所有数据
     * @return
     */
    @RequestMapping("/allUsers")
    public List<Users> getAllUsers() {
        return user.selectList(null);
    }

}
```
如上代码所示，写了一些使用案例，但不止这些方法，还有更多。

### 2.注解形式
#### Mapper层
```java
package com.blaife.mapper;

import com.blaife.model.Users;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 第二种：注解形式
 */
public interface UsersAnnotationMapper {

    /**
     * 根据用户名查询用户信息
     * @return
     */
    @Select("SELECT id, `name`, age FROM users WHERE `name` = #{name}")
    List<Users> getUserByName(String name);
}
```
#### Service层
```java
package com.blaife.service.impl;

import com.blaife.mapper.UsersAnnotationMapper;
import com.blaife.model.Users;
import com.blaife.service.UsersAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersAnnotationServiceImpl implements UsersAnnotationService {

    @Autowired
    @SuppressWarnings(value = "all")
    UsersAnnotationMapper users;

    @Override
    public List<Users> getUserByName(String name) {
        return users.getUserByName(name);
    }
}
```
#### Controller层
```java
package com.blaife.controller;

import com.blaife.service.UsersXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xml")
public class UsersXmlController {

    @Autowired
    UsersXmlService users;

    @RequestMapping("/insertUserMultiterm")
    public String insertUserMultiterm() {
        int r = users.insertUserMultiterm();
        if (r > 0) {
            return "数据插入成功";
        } else {
            return "数据插入失败";
        }
    }

}
```

### 3.映射文件形式
#### Mapper层
```java
package com.blaife.mapper;

import com.blaife.model.Users;

import java.util.List;

public interface UsersXmlMapper {

    /**
     * 插入多条语句，印证动态sql foreach
     * @param list
     * @return
     */
    int insertUserMultiterm(List<Users> list);

}
```
#### Mapper.xml映射文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="com.blaife.mapper.UsersXmlMapper" >

    <insert id="insertUserMultiterm" parameterType="java.util.List">
        INSERT INTO users (id, `name`, age) VALUES
        <foreach collection="list" item="item" separator="," close=";">
            (#{item.id}, #{item.name}, #{item.age})
        </foreach>
    </insert>

</mapper>
```
#### Service层
```java
package com.blaife.service.impl;

import com.blaife.mapper.UsersAnnotationMapper;
import com.blaife.model.Users;
import com.blaife.service.UsersAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersAnnotationServiceImpl implements UsersAnnotationService {

    @Autowired
    @SuppressWarnings(value = "all")
    UsersAnnotationMapper users;

    @Override
    public List<Users> getUserByName(String name) {
        return users.getUserByName(name);
    }
}
```
#### Controller层
```java
package com.blaife.controller;

import com.blaife.service.UsersXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xml")
public class UsersXmlController {

    @Autowired
    UsersXmlService users;


    @RequestMapping("/insertUserMultiterm")
    public String insertUserMultiterm() {
        int r = users.insertUserMultiterm();
        if (r > 0) {
            return "数据插入成功";
        } else {
            return "数据插入失败";
        }
    }

}
```