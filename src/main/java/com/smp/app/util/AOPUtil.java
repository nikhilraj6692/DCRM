package com.smp.app.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

public class AOPUtil {

    private JavaMailSenderImpl mail;

    public void setMail(JavaMailSenderImpl mail) {
        this.mail = mail;
    }


    @Async
    public void sendMail(String from, List<String> to, String subject, String msg)
        throws UnsupportedEncodingException, MessagingException {
        System.setProperty("mail.mime.charset", "UTF-8");
        MimeMessage message = mail.createMimeMessage();
        message.setHeader("Content-Type", "text/plain; charset=UTF-8");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String[] toEmails = new String[to.size()];
        toEmails = to.toArray(toEmails);

        try {
            helper.setTo(toEmails);
            helper.setFrom(from);
            helper.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            helper.setText(msg, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mail.send(message);

    }
}
