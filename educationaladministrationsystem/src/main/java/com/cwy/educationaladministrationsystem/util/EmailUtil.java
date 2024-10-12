package com.cwy.educationaladministrationsystem.util;

import jakarta.annotation.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    private static JavaMailSender javaMailSender;

    @Resource
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        EmailUtil.javaMailSender = javaMailSender;
    }

    public static boolean sendTextMail(String from, String to, String subject, String mailContent) {
        ;
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(mailContent);
            javaMailSender.send(message);
            return Boolean.TRUE;
        } catch (MailException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
