package tk.mybatis.springboot.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender sender;


    String subject1 = "主题：生日回访邮件";
    String content1 = "祝您生日快乐！！";

    String fromUser = "15626154123@163.com";
    String fromPersonal = "CRM公司";


    //"1070506780@qq.com"
    public boolean sendEmail(String[] toUsers, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        InternetAddress fromAddress = new InternetAddress(fromUser, fromPersonal, "UTF-8");
        messageHelper.setFrom(fromAddress); //设置发件人

        messageHelper.setTo(toUsers);   //设置收件人
        System.out.println("收件人：" + Arrays.toString(toUsers));
        messageHelper.setSubject(subject);  //设置邮件主题
        mimeMessage.setText(content);   //设置邮件内容

        sender.send(mimeMessage);
        return true;
    }

    public String getSubject1() {
        return subject1;
    }

    public String getContent1() {
        return content1;
    }
}
