package tk.mybatis.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testApp {

    @Autowired
    JavaMailSender sender;

   /* @Autowired
    Session session;*/

    @Test
    public void test() throws UnsupportedEncodingException, MessagingException {

//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(new InternetAddress("15626154123@163.com", "xx公司", "UTF-8"));
        messageHelper.setTo("1070506780@qq.com");

        messageHelper.setSubject("主题 测试");
        mimeMessage.setText("123text 测试");

        /*mimeMessage.setFrom(new InternetAddress("test@chinas.com", "这里是需要的昵称", "UTF-8"));
        mimeMessage.setFrom("15626154123@163.com");
        mimeMessage.setText("123text");
        mimeMessage.setSubject("test mail subject");
        mimeMessage.set("1070506780@qq.com");*/
       /* mailMessage.setFrom("15626154123@163.com");
        mailMessage.setText("123text");
        mailMessage.setSubject("test mail subject");
        mailMessage.setTo("1070506780@qq.com");*/

        sender.send(mimeMessage);

    }
}
