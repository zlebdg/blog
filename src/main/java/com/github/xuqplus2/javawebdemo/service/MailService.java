package com.github.xuqplus2.javawebdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Autowired
    JavaMailSenderImpl javaMailSender;

    public MimeMessage createMimeMessage(String subject, String text, String from, String... to)
            throws MessagingException {
        // 使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
        // MimeMessages为复杂邮件模板，支持文本、附件、html、图片等。
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);
        // mimeMessageHelper.addAttachment("resource", resource);
        return mimeMessage;
    }

    public SimpleMailMessage createSimpleMailMessage(
            String subject, String text, String from, String... to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        return simpleMailMessage;
    }
}
