package com.iquest.service.impl;

import com.iquest.service.MailService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service("mailService")
public class MailServiceImpl implements MailService {

    final String username;
    final String password;
    final Properties props;
    private List<String> subscribers;

    public MailServiceImpl() {
        this.username = "sd.assignment3@gmail.com";
        this.password = "stef654321";

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    public void sendMail(String to, String subject, String content) {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(content, "text/html");

            Transport.send(message);

            System.out.println("Mail sent.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
