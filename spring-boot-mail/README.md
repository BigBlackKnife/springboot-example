# springboot-mail 邮件服务
springboot2.0集成了mail模块， 邮件一般用与注册验证、营销推广、忘记密码等内容，使用还是比较普遍的。
之前都是使用JavaMail相关内容来写邮件服务，现在springboot已经集成，使用更加方便了。

## 注意
使用邮件服务是需要邮箱授权的（我使用的是qq邮箱，其他邮箱暂无了解），QQ邮箱需要先SMTP授权，这里就不写具体授权方法了，授权之后会得到一个授权码，我们需要的就是这个。

## 引入依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

## 配置邮件信息
```properties
spring.mail.host=smtp.qq.com
spring.mail.username=blackknife@foxmail.com
# password不是邮箱密码 而是授权码
spring.mail.password=XXXXXXXXXXXXXXXX
spring.mail.default-encoding=UTF-8
```

## 发送文本邮件
```java
@Component
public class MailUtil {
    // 发送人 ： 需要和发送的邮箱信息一致
    @Value("${spring.mail.username}")
    private String sender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 邮件方法发送时的类，在引入springbootmail后生成
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单邮件(文本邮件)
     * @param to 接收人邮箱地址
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        // 请注意这里使用的是SimpleMailMessage，之后更复杂的例子使用的就不是这个类了
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("简单邮件发送成功");
        } catch (Exception e) {
            logger.error("简单邮件发送时出现异常", e);
        }
    }
}
```

### 测试
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {
    @Autowired
    private MailUtil mail;

    /**
     * 测试简单邮件
     */
    @Test
    public void testSimpleMail() {
        mail.sendSimpleMail("blackknife@foxmail.com","test simple mail"," hello this is simple mail");
    }
}
```

## 发送html邮件
```java
@Component
public class MailUtil {
    // 发送人: 需要和发送的邮箱信息一致
    @Value("${spring.mail.username}")
    private String sender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 邮件方法发送时的类，在引入springbootmail后生成
    @Autowired
    private JavaMailSender mailSender;

   /**
    * 发送html邮件
    * @param to 接收人
    * @param subject 主题
    * @param content 内容
    */
   public void sendHtmlMail(String to, String subject, String content) {
       // MimeMessage 之后的例子使用的都是这个对象
       MimeMessage message = mailSender.createMimeMessage();
       try {
           //true表示需要创建一个multipart message(多重信息)
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
}
```

### 测试
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {
    @Autowired
    private MailUtil mail;

    /**
     * 测试html邮件
     */
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
        mail.sendHtmlMail("blackknife@foxmail.com","test html mail",content);
    }
}
```

## 发送带附件的邮件
```java
@Component
public class MailUtil {
    // 发送人: 需要和发送的邮箱信息一致
    @Value("${spring.mail.username}")
    private String sender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 邮件方法发送时的类，在引入springbootmail后生成
    @Autowired
    private JavaMailSender mailSender;

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
}
```

### 测试
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {
    @Autowired
    private MailUtil mail;

    /**
     * 测试带附件的邮件发送
     */
    @Test
    public void testAttachmentsMail() {
        String filePath="C:\\Users\\Blaife\\Desktop\\test.jpg,C:\\Users\\Blaife\\Desktop\\test.jpg";
        mail.sendAttachmentsMail("blackknife@foxmail.com", "test attachments mail", "有附件,请查收", filePath);
    }
}
```

## 发送带有静态文件的邮件(图片)
```java
@Component
public class MailUtil {
    // 发送人: 需要和发送的邮箱信息一致
    @Value("${spring.mail.username}")
    private String sender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 邮件方法发送时的类，在引入springbootmail后生成
    @Autowired
    private JavaMailSender mailSender;

   /**
    * 发送含有静态资源的邮件（图片）
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
}
```

### 测试
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {
   @Autowired
   private MailUtil mail;

   /**
    * 测试带静态文件（图片）的邮件发送
    */
   @Test
   public void testInlineResourceMail() {
       String rscId = "blaifeInline";
       String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
       String imgPath = "C:\\Users\\Blaife\\Desktop\\test.jpg";
       mail.sendInlineResourceMail("blackknife@foxmail.com", "主题：这是有图片的邮件", content, imgPath, rscId);
   }
}
```

## thymeleaf模板化邮件
其实本质上还是html邮件，使用thymeleaf模板化生成html文件
### 引入依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 编码模板文件
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

### 工具类方法
```java
@Component
public class MailUtil {
    // 发送人: 需要和发送的邮箱信息一致
    @Value("${spring.mail.username}")
    private String sender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 邮件方法发送时的类，在引入springbootmail后生成
    @Autowired
    private JavaMailSender mailSender;

    // 自己调自己
    @Autowired
    private MailUtil mail;

    /**
    * 发送html邮件
    * @param to 接收人
    * @param subject 主题
    * @param content 内容
    */
   public void sendHtmlMail(String to, String subject, String content) {
       // MimeMessage 之后的例子使用的都是这个对象
       MimeMessage message = mailSender.createMimeMessage();
       try {
           //true表示需要创建一个multipart message(多重信息)
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

   /**
    * 发送模板化的验证邮件
    * @param name 用户name
    * @param id 用户id
    */
   public void sendTemplateVerifyMail(String to, String subject, String name, String id) {
       Context context = new Context();
       context.setVariable("name", name);
       context.setVariable("id", id);
       String emailContent = templateEngine.process("verifyMail", context);
       System.out.println(emailContent);
       mail.sendHtmlMail(to, subject, emailContent);
   }
}
```

### 测试
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {
   @Autowired
   private MailUtil mail;a

   /**
    * 测试模板化的mail发送
    */
   @Test
   public void testTemplateMail() {
       mail.sendTemplateVerifyMail("conner_dong@163.com", "主题：这是模板化的邮件", "blaife", "0121") ;
   }
}
```