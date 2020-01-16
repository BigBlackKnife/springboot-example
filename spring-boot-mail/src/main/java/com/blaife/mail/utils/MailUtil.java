package com.blaife.mail.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailUtil {

    @Value("${spring.mail.username}")
    private String sender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 邮件方法发送时的类，在引入springbootmail后生成
    @Autowired
    private JavaMailSender mailSender;

    // template 模板化
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailUtil mail;

    /**
     * 发送简单邮件(文本邮件)
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
            logger.info("简单邮件发送成功");
        } catch (Exception e) {
            logger.error("简单邮件发送时出现异常", e);
        }
    }

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