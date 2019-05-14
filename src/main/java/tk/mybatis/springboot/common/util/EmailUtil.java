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


    String subjectForBirthday = "主题：生日回访邮件";
    String contentForBirthday = "祝您生日快乐！！";


    String subjectForDrain = "主题：客户回访邮件";
    String contentForDrain = "请问您对我们的服务有何建议？";


    String subjectForOrder = "主题：订单回访邮件";
    String contentForOrder = "请问您对我们的产品有何建议？";


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

    public String getSubjectForBirthday() {
        return subjectForBirthday;
    }

    public String getContentForBirthday() {
        return contentForBirthday;
    }

    public String getSubjectForDrain() {
        return subjectForDrain;
    }

    public String getContentForDrain() {
        return contentForDrain;
    }

    public String getSubjectForOrder() {
        return subjectForOrder;
    }

    public String getContentForOrder() {
        return contentForOrder;
    }
}
