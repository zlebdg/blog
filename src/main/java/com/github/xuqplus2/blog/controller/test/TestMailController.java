package com.github.xuqplus2.blog.controller.test;

import com.github.xuqplus2.blog.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("blog/test/mail")
@Slf4j
public class TestMailController {
    @Autowired
    MailService mailService;
    @Autowired
    JavaMailSenderImpl javaMailSender;
    @Value("${postfix.username}")
    String from;

    @PostMapping()
    public String sendSimpleMailMessage(String subject, String text, String... to) {
        javaMailSender.send(mailService.createSimpleMailMessage(subject, text, from, to));
        log.info("邮件已经发出, subject={}, text={}, from={}, to={}", subject, text, from, to);
        return "ok";
    }
}
