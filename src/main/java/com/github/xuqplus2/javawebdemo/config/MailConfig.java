package com.github.xuqplus2.javawebdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
public class MailConfig {

    private static JavaMailSenderImpl javaMailSender;

    @Value("${postfix.host}")
    String host;

    @Value("${postfix.port}")
    Integer port;

    @Value("${postfix.protocol}")
    String protocol;

    @Value("${postfix.username}")
    String username;

    @Value("${postfix.password}")
    String password;

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        log.info("时间={}, ", DateTime.now().toString("yyyy-MM-dd HH:mm:ss.sss"));
        if (null == javaMailSender) {
            javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setDefaultEncoding("utf-8");
            javaMailSender.setHost(host);
            javaMailSender.setPort(port);
            javaMailSender.setProtocol(protocol);
            javaMailSender.getJavaMailProperties().setProperty("mail.smtp.localhost", host);
            if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {

            } else {
                javaMailSender.setUsername(username);
                javaMailSender.setPassword(password);
            }
        }
        return javaMailSender;
    }
}
