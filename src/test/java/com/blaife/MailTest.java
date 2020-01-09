package com.blaife;

import com.blaife.utils.mail.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void testSimpleMail() throws Exception {
        mailUtil.sendSimpleMail("1346502134@qq.com","2020/1/9 温玖","你有想我吗");
    }

    @Test
    public void testSend() throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("blackknife@foxmail.com");
        message.setTo("439427296@qq.com");
        message.setSubject("这是标题");
        message.setText("这是内容");
        javaMailSender.send(message);
    }


}
