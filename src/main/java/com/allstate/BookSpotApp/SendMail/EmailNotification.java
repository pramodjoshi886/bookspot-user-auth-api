package com.allstate.BookSpotApp.SendMail;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailNotification {
    private static Logger logger = LoggerFactory.getLogger(EmailNotification.class);

    public static void sendMail(String email, String message1, String subject, String content) {

        java.util.Properties properties = new java.util.Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        javax.mail.Session mailSession = javax.mail.Session.getInstance(properties);
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setContent(content + "<h1>" + message1 + "</h1>", "text/html");
            message.setSubject(subject);
            InternetAddress sender = new InternetAddress("thameem.syed93@gmail.com", "Book Spot");
            InternetAddress receiver = new InternetAddress(email);
            message.setFrom(sender);
            message.setRecipient(Message.RecipientType.TO, receiver);
            message.saveChanges();
            javax.mail.Transport transport = mailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", 587, "thameem.syed93@gmail.com", "oqwdnvjeeealxivd");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (Exception e) {
            System.out.println(e);
            logger.error("error while sending mail");
        }

    }

}
