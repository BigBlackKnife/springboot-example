package com.blaife.utils.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

    /**
     * 发送人署名
     */
    public final static String MAIL_ADDR = "blackknife@foxmail.com";

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
        message.setFrom(MAIL_ADDR);
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
