package com.github.xuqplus2.javawebdemo.controller.test;

import com.github.xuqplus2.javawebdemo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/mail")
public class TestMailController {
    @Autowired
    MailService mailService;
    @Autowired
    JavaMailSenderImpl javaMailSender;

    @PostMapping()
    public String sendSimpleMailMessage(String subject, String text, String from, String... to) {
        javaMailSender.send(mailService.createSimpleMailMessage(subject, text, from, to));
        return "ok";
    }
}
