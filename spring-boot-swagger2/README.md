# Spring-Boot-Swagger2
每个系统都需要一份接口文档，方便各个团队之间的交流。但是在微服务快速发展的今天，
各个系统之间的交流基本都依赖于接口调用，这样就导致接口变得越来越多，开发人员在开发的同时还要维护文档，
任务量加重，哀声一片，也可能会出现开发人员未及时维护文档，使得文档与接口信息不一致。  
Swagger是一款可以通过注解的方式生成Restful APIs交互式界面的工具，它不仅减少了我们文档的编写，
而且同时能够实现接口测试，接口修改的同时也能够维护文档，可以很好的避免信息不一致。
虽然Swagger具有如此多的优点，但是它的缺点也不容忽视，代码侵入严重，在原有代码的基础上，
我们需要写更多的注解。

## 添加swagger依赖
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
```
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

## Swagger2配置文件
```java
package com.blaife.swagger2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 配置 Docket Bean
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.blaife.swagger2.controller")) // 配置映射路径和要扫描的接口的位置
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    /**
     * 配置一下Swagger2文档网站的信息，例如网站的title，网站的描述，联系人的信息，使用的协议等等。
     * @return
     */
    public ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("测试springboot2采用swagger2构建RESTFul APIs")// 标题
                .description("通过访问swagger-ui.htmnl,实现接口测试，文档生成。") // 说明
                .version("1.0") // 版本
                .contact(new Contact("blaife", "https://github.com/BigBlackKnife", "2767026270@qq.com")) // 联络方式
                .license("许可证书") // 许可证书
                .licenseUrl("http://www.baidu.com") // 证书地址
                .build();
    }
}
```

## 编写实体类
```java
package com.blaife.swagger2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class User {
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("用户密码")
    private String pwd;

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
```
实体类中可以使用swagger注解来配置属性的含义，在接口中使用到这个实体类时就可以用到了。

## 编写接口
```java
package com.blaife.swagger2.controller;

import com.blaife.swagger2.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试Swagger2文档显示")
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "获取一个测试字符串") // 标记一个方法的作用
    @GetMapping("/getString")
    public String getString() {
        return "获取一个测试字符串";
    }

    @ApiOperation(value = "输出User信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "用户名", defaultValue = "blaife", required = true),
        @ApiImplicitParam(name = "pwd", value = "用户密码", defaultValue = "0121", required = true)
    })
    @PostMapping("/showUser")
    public User showUser(String name, String pwd) {
        User user = new User();
        user.setName(name);
        user.setPwd(pwd);
        return user;
    }

    @ApiOperation(value = "获取user对象")
    @PostMapping("/getUser")
    public User getUser(User user) {
        return user;
    }
}
```

接口一共为3个，getString为没有参数的方法，showUser为有基本类型参数的方法，getUser为有实体类参数的方法（配合User实体类）。

## Swagger注解

### 类注解：@Api

修饰controller，用来描述该controller所代表的含义。其中比较常见的有如下几个参数：
- tags：指定该controller的标签，也就说该controller的含义。例如用户模块、权限模块等。
- producer：指定controller统一采用的响应格式。
- protocols：指定协议。

### 对象注解：@ApiModel, @ApiModelProperty

- @ApiModel：被用来描述某个对象。可以是接口请求参数对象，也可以是响应对象。
- @ApiModelProperty：用于描述对象字段所代表的含义。

### 方法注解：@ApiOperation

修饰controller下面的方法，用来描述该方法的作用。其中比较常见的有如下几个参数：
- value：描述该方法用途。
- note：对该方法的内容进行详细描述。
- httpMethod：使用什么http方法。

### 参数注解：@ApiImplicitParam，@ApiImplicitParam

@ApiImplicitParam：用于描述请求参数，一个该注解描述一个参数。其中比较常见的有如下几个参数：
- name：参数名。
- value： 参数的含义。
- required：参数是否必传。
- paramType：参数所存在的位置。有一下几种类型：
    - header：表示从header中获取。@RequestHeader
    - query：表示从地址栏问号后面获取。@RequestParam
    - path：表示从URL中获取。@PathVariable
    - body：表示从body中获取。
    - form：表示从form表单中获取。
- dataType：参数类型，默认String。
- defaultValue：参数的默认值。

@ApiImplicitParams：用于描述多个参数。可以包含多个@ApiImplicitParam。

### 响应注解：@ApiResponse，@ApiResponses

@ApiResponse：用于描述HTTP其中的一种响应。其中比较常见的有如下几个参数：
- code：int类型，用于表示状态码。例如：405。
- message：用于描述该码值所代表的具体含义。
- response：抛出异常类。

@ApiResponses：用于描述多个HTTP响应码。可以包含多个@ApiResponse。

### 其他注解：@ApiIgnore

该注解表示，swagger2在生成文档的时候，忽略该方法或类。