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


    String subjectForBirthday = "主题：生日祝福";
    String contentForBirthday = "亲爱的顾客，您好，这里是来自xx公司的生日祝福。今天是顾客您的生日，在此本公司祝您生日快乐了，希望您在接下来的日子里心想事成，万事如意，福如东海，寿比南山。而我们也会在这里陪您度过一个又一个的生日，为您提供优质的服务，很感谢您对本公司的支持。";


    String subjectForDrain = "主题：流失客户回件";
    String contentForDrain = "亲，这里是xx公司，我们了解到您已经很有没有光顾过我们公司了呢，这让我们很伤心，如果是上次的购买体验并不愉快，您可以把您的意见发送给我们，我们一定会多加改正的，并且我们会对您的下次订单给予一定的优惠。请您相信我们公司是一个大公司，所有的商品都是保质保量，提供完善的售后服务，我们可以给您提供优质的购物体验，请您不要放弃我们好吗？如果您对公司的管理或者其他方面有什么建议，可以写邮件发到我们公司的邮箱，我们很高兴接受您的建议，您的建议是我们进步的动力。\n";


    String subjectForOrder = "主题：订单回访";
    String contentForOrder = "亲，这里是xx公司，您在本公司的下的订单已经过去一个月了，请问您收到您购买的东西了么？如果还没有收到请及时联系您对应的负责人，我们公司会帮您查看订单物流并催促提供解决措施，如果收到的货物有什么问题，也请您将问题反映给您的负责人，我们公司保证会妥善解决给您提供一个满意的答复。如果您对负责人有什么意见，可以写下来发送到公司邮箱，如果属实我们将帮您更换负责人并向您表达我们真挚的歉意。如果没有其他情况，那您已经完成了本次购物，我们公司期待您的下次光临。我们会不断的提升我们的服务，只为顾客您的满意。感谢您对本公司的支持，xx公司与您同行。\n";


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
