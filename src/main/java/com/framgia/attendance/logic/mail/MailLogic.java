package com.framgia.attendance.logic.mail;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailLogic implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(MailLogic.class);

    private ConfigMail mailConfig;

    public MailLogic(ConfigMail mailConfig) {
        // TODO Auto-generated constructor stub
        this.mailConfig = mailConfig;
    }

    public boolean sendMail(String to, String cc, String bcc, String subject,
            String content) {

        boolean sent = false;
        Session session = newSession();
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(mailConfig.MAIL_FROM));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            
            if (StringUtils.isNotEmpty(cc)) {
                message.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(cc));
            }
            
            if (StringUtils.isNotEmpty(bcc)) {
                message.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(bcc));
            }
            
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
            sent = true;
        } catch (MessagingException e) {
            LOGGER.error("Failed to send mail.", e);
        }
        return sent;
    }

    private Session newSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", mailConfig.HOST_NAME);
        props.put("mail.smtp.port", mailConfig.PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");

        if (mailConfig.PORT.equals("465")) {
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.socketFactory.port", "465");
        }

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.USER_NAME,
                        mailConfig.PASSWORD);
            }
        });
    }
}
