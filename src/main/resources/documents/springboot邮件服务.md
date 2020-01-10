# 发送简单邮件
## 1.引入pom依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

## 2.编写配置文件
```properties
spring.mail.host=smtp.qq.com
spring.mail.username=blackknife@foxmail.com
# password不是邮箱密码 而是授权码
spring.mail.password=XXXXXXXXXXXXX
spring.mail.default-encoding=UTF-8
```

## 3.编写MailUtil工具类
```java
package com.blaife.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

    @Value("${spring.mail.username}")
    private String sender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单邮件
     * @param to 接收人邮箱地址
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("简单邮件已发送");
        } catch (Exception e) {
            logger.error("简单邮件发送失败", e);
        }
    }

}
```
JavaMailSender对象是引入依赖于生成的对象，所以这里添加@Autowired注解进行获取。  
这里需要注意的是`From`需要与自己配置文件中`username`的内容一致.不一致时会提示`502`错误


## 4.编写测试方法
```java
package com.blaife;

import com.blaife.utils.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringBootMail {

    @Autowired
    private MailUtil mail;

    @Test
    public void testSimpleMail() {
        mail.sendSimpleMail("conner_dong@163.com","test simple mail"," hello this is simple mail");
    }
}
```

# 发送html邮件
## 添加公共方法
其他配置信息都不需要改动，我们在MailUtil文件中写一下公共方法
```java
/**
 * 发送html邮件
 * @param to 接收人
 * @param subject 主题
 * @param content 内容
 */
public void sendHtmlMail(String to, String subject, String content) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
        //true表示需要创建一个multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        logger.info("html邮件发送成功");
    } catch (MessagingException e) {
        logger.info("html邮件发送时出现异常");
    }
}
```
这个方法中我们需要注意的是它不再使用`SimpleMailMessage`而是MimeMessage,
并且需要MimeMessageHelper对其进行处理，最后是text属性添加时需要加入第二个参数`true`,表示发送的html邮件。

## 编写测试方法
```java
@Test
public void testHtmlMail() {
    String content="<html>\n" +
            "<head>\n" +
            "   <style  type=\"text/css\">\n" +
            "       h3 {color: red;}"+
            "   </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
            "</body>\n" +
            "</html>";
    mail.sendHtmlMail("conner_dong@163.com","test html mail",content);
}
```

# 发送带附件的邮件
## 先写一下公共方法
```java
/**
 * 发送带附件的邮件
 * @param to
 * @param subject
 * @param content
 * @param filePaths 多个path之间使用","分割
 */
public void sendAttachmentsMail(String to, String subject, String content, String filePaths){
    MimeMessage message = mailSender.createMimeMessage();
    try {
        //true表示需要创建一个multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        String[] files = filePaths.split(",");
        for (String filePath : files) {
            FileSystemResource file = new FileSystemResource(new File(filePath));
            helper.addAttachment(file.getFilename(), file);
        }
        mailSender.send(message);
        logger.info("包含附件的邮件发送成功");
    } catch (MessagingException e) {
        logger.info("包含附件的邮件发送时出现异常");
    }
}
```

## 测试方法
```java
@Test
public void testAttachmentsMail() {
    String filePath="E:\\Example\\SpringBoot-Example\\src\\test\\java\\com\\blaife\\TestSpringBootMail.java,E:\\Example\\SpringBoot-Example\\src\\main\\java\\com\\blaife\\utils\\MailUtil.java";
    mail.sendAttachmentsMail("conner_dong@163.com", "test attachments mail", "有附件,请查收", filePath);
}
```

# 发送带静态文件（图片的邮件）
## 公共方法
```java
/**
 * 测试带附件的邮件发送
 * @param to 接收人
 * @param subject 主题
 * @param content 内容
 * @param rscPath 静态资源地址
 * @param rscId 静态资源id
 */
public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);
        mailSender.send(message);
        logger.info("嵌入静态资源的邮件已经发送。");
    } catch (MessagingException e) {
        logger.error("发送嵌入静态资源的邮件时发生异常！", e);
    }
}
```

## 测试方法
```java
@Test
public void testInlineResourceMail() {
    String rscId = "blaifeInline";
    String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
    String imgPath = "C:\\Users\\Blaife\\Desktop\\test.jpg";
    mail.sendInlineResourceMail("conner_dong@163.com", "主题：这是有图片的邮件", content, imgPath, rscId);
}
```

# 邮件模板
## 1.引入thymeleaf依赖包
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
## 2.编写模板文件
```html
<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    您好,<span th:text="${name}"></span>,这是验证邮件,请点击下面的链接完成验证,<br/>
    <a href="#" th:href="@{ http://localhost:9999/blaife/{id}(id=${id}) }">激活账号</a>
</body>
</html>
```
我们需要注意的是：
- xmlns:th="http://www.thymeleaf.org： 为thymeleaf命名空间，必须引入。
- th:href="@{~/css/app.css}:  以绝对路径的方式引入样式表文件
- th:text="${title}"： 以文本的方式显示title值

## 3.编写公共方法
```java
/**
 * 发送模板化的验证邮件
 * @param name 用户name
 * @param id 用户id
 */
public void sendTemplateVerifyMail(String to, String subject, String name, String id) {
    Context context = new Context();
    context.setVariable("name", name);
    context.setVariable("id", id);
    String emailContent = templateEngine.process("mail/testTemplateVerify", context);
    System.out.println(emailContent);
    mail.sendHtmlMail(to, subject, emailContent);
}
```

## 4.测试方法
```java
@Test
public void testTemplateMail() {
    mail.sendTemplateVerifyMail("conner_dong@163.com", "主题：这是模板化的邮件", "blaife", "0121") ;
}
```