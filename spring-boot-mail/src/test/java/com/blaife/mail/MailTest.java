package com.blaife.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private com.blaife.mail.utils.MailTest mail;

    /**
     * 测试简单邮件
     */
    @Test
    public void testSimpleMail() {
        mail.sendSimpleMail("conner_dong@163.com","test simple mail"," hello this is simple mail");
    }

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
        mail.sendHtmlMail("conner_dong@163.com","test html mail",content);
    }

    /**
     * 测试带附件的邮件发送
     */
    @Test
    public void testAttachmentsMail() {
        String filePath="C:\\Users\\Blaife\\Desktop\\test.jpg,C:\\Users\\Blaife\\Desktop\\test.jpg";
        mail.sendAttachmentsMail("conner_dong@163.com", "test attachments mail", "有附件,请查收", filePath);
    }

    /**
     * 测试带静态文件（图片）的邮件发送
     */
    @Test
    public void testInlineResourceMail() {
        String rscId = "blaifeInline";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "C:\\Users\\Blaife\\Desktop\\test.jpg";
        mail.sendInlineResourceMail("conner_dong@163.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }

    /**
     * 测试模板化的mail发送
     */
    @Test
    public void testTemplateMail() {
        mail.sendTemplateVerifyMail("conner_dong@163.com", "主题：这是模板化的邮件", "blaife", "0121") ;
    }

}
