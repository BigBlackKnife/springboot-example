# Thymeleaf

## 1.特点

### 1.1 动静结合
Thymeleaf 在有网络和无网络的环境下皆可运行，即它可以让美工在浏览器查看页面的静态效果，也可以让程序员在服务器查看带数据的动态页面效果。这是由于它支持 html 原型，然后在 html 标签里增加额外的属性来达到模板+数据的展示方式。浏览器解释 html 时会忽略未定义的标签属性，所以 thymeleaf 的模板可以静态地运行；当有数据返回到页面时，Thymeleaf 标签会动态地替换掉静态内容，使页面动态显示。

### 1.2 开箱即用
它提供标准和spring标准两种方言，可以直接套用模板实现JSTL、 OGNL表达式效果，避免每天套模板、改jstl、改标签的困扰。同时开发人员也可以扩展和创建自定义的方言。

### 1.3 多方言支持
Thymeleaf 提供spring标准方言和一个与 SpringMVC 完美集成的可选模块，可以快速的实现表单绑定、属性编辑器、国际化等功能。

### 1.4 与springboot完美整合
SpringBoot提供了Thymeleaf的默认配置，并且为Thymeleaf设置了视图解析器，我们可以像以前操作jsp一样来操作Thymeleaf。代码几乎没有任何区别，就是在模板语法上有区别。

---
## 2.内置对象

### 2.1 环境相关对象
|语法|解释|
|---|---|
|#ctx| 获取Thymeleaf自己的Context对象|
|#requset| 如果是web程序，可以获取HttpServletRequest对象|
|#response| 如果是web程序，可以获取HttpServletReponse对象|
|#session| 如果是web程序，可以获取HttpSession对象|
|#servletContext| 如果是web程序，可以获取HttpServletContext对象|

### 2.2 全局对象
|语法|解释|
|---|---|
|#dates| 处理java.util.date的工具对象|
|#calendars| 处理java.util.calendar的工具对象|
|#numbers| 用来对数字格式化的方法|
|#strings| 用来处理字符串的方法|
|#bools| 用来判断布尔值的方法|
|#arrays| 用来护理数组的方法|
|#lists| 用来处理List集合的方法|
|#sets|	用来处理set集合的方法|
|#maps|	用来处理map集合的方法|

---
## 3.常用语法

### 3.1 变量
##### 基本变量 `th:text="${XXX}"`
##### 自定义变量 `th:object="${XXXX}"`

自定义变量的主要作用是分级，给编码提供便捷的输入。如：生日并没有使用自定义变量。

```html
<table>
    <tr th:object="${user}">
        <td th:text="*{name}">未命名</td>
        <td th:text="*{nickName}">未命名</td>
        <td th:text="*{sex}">未知</td>
        <td th:text="*{age}">0</td>
        <td th:text="${user.birthday}">2020-1-1</td>
    </tr>
</table>
```

### 3.2 方法
ognl表达式本身就支持方法调用
```html
<!-- split方法为例：-->
<span th:text="${mothodTest.split('，')[0]}"></span><br />
<span th:text="${mothodTest.split('，')[1]}"></span>
```

### 3.3 字面值
##### 字符串
使用单引号引用：
```html
<span th:text="${'thymeleaf'}"></span><br />
```
##### 数字
数字不需要任何特殊语法，而且可以直接进行算术运算
```html
<span th:text="${652 + 3}"></span><br />
```

### 3.4 拼接
普通字符串与表达式拼接
```
<!-- '' 拼接 -->
<span th:text="'欢迎您:' + ${user.name} + '!'"></span><br />
<!-- | 拼接 -->
<span th:text="|欢迎您:${user.name}|"></span>
```

### 3.5 运算
${}内部的是通过OGNL表达式引擎解析的，外部的才是通过Thymeleaf的引擎解析，因此运算符尽量放在${}外进行。

##### 算数运算
支持的算术运算符：+ - * / %
```html
<span th:text="${user.age}%2"></span>
```

##### 比较运算
支持的比较运算：>, <, >= and <= ，但是>, <不能直接使用，因为xml会解析为标签，要使用别名。
注意 == and !=不仅可以比较数值，类似于equals的功能。  

可以使用的别名：gt (>), lt (<), ge (>=), le (<=), not (!). Also eq (==), neq/ne (!=).
```html
<span th:text="${user.age} > 18"></span>
```

##### 条件运算
三元运算符：
```
<span th:text="${user.sex} ? '男':'女'"></span>
```
默认值:  
有的时候，我们取一个值可能为空，这个时候需要做非空判断，可以使用 表达式 ?: 默认值简写：
```
<span th:text="${user.name} ?: '未知'"></span>
```

### 3.6 逻辑判断 `th:if` & `th:unless`
Thymeleaf中使用th:if 或者 th:unless ，两者的意思恰好相反。

```html
if：
<span th:text="|${user.name}的年龄为${user.age}, 大于18岁|" th:if="${user.age} > 18"></span>
unless：
<span th:text="|${user.name}的年龄为${user.age}, 小于等于25岁|" th:unless="${user.age} > 25"></span>
```

### 3.7 循环 `th:each`

##### 可遍历类型
- Iterable，实现了Iterable接口的类
- Enumeration，枚举
- Interator，迭代器
- Map，遍历得到的是Map.Entry
- Array，数组及其它一切符合数组结果的对象

```html
<table>
    <tr th:each="user : ${users}">
        <td th:text="${user.name}"></td>
        <td th:text="${user.age}"></td>
    </tr>
</table>
```

##### 迭代状态对象属性
- index，从0开始的角标
- count，元素的个数，从1开始
- size，总元素个数
- current，当前遍历到的元素
- even/odd，返回是否为奇偶，boolean值
- first/last，返回是否为第一或最后，boolean值

```html
<table>
    <tr th:each="user,stat : ${users}">
        <td th:text="${stat.count}"></td>
        <td th:text="${user.name}"></td>
        <td th:text="${user.age}"></td>
        <td th:text="${stat.size}" th:if="${stat.last}"></td>
    </tr>
</table>
```

### 3.8 分支控制 `th:switch="${XXX}"`

##### 注意

```html
<span th:switch="${user.sex}">
    <p th:case="'0'">女的</p>
    <p th:case="'1'">男的</p>
    <p th:case="*">不知道是啥</p>
</span>
```

- 需要注意的是，一旦有一个th:case成立，其它的则不再判断。与java中的switch是一样的。
- th:case="*"表示默认，放最后。

### 3.9 js模板
---


## 4.与springboot整合

### 4.1 pom 依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 4.2 配置文件
```properties
# 生产环境可以打开缓存 默认位true
spring.thymeleaf.cache=false
# 设置编码格式 默认位UTF-8
spring.thymeleaf.encoding=UTF-8
# 设置前缀 默认为classpath:/templates/
# spring.thymeleaf.prefix=classpath:/templates/
# 设置后缀 默认为html
# spring.thymeleaf.suffix=html
```

### 4.3 基本操作

##### 接口
```java
package com.blaife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Blaife
 * @description 测试接口
 * @data 2020/4/28 16:04
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    public String test(Model model) {
        String test = "接口test";
        model.addAttribute("txt", test);
        return "test";
    }

}
```

##### html页面
```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>测试</title>
</head>
<body>
    <div th:text="${txt}"></div>
</body>
</html>
```

### 4.4 注意事项

- 接口不能添加`@ResponsBody`以及`@RestController`注解，否则视图解析器会作为普通字符串进行处理