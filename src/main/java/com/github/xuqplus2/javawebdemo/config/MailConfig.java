package com.github.xuqplus2.javawebdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.StringUtils;

@Configuration
public class MailConfig {

  private static JavaMailSenderImpl javaMailSender;
  private static final Logger LOGGER = LoggerFactory.getLogger(MailConfig.class);

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
    if (null == javaMailSender) {
      javaMailSender = new JavaMailSenderImpl();
      javaMailSender.setDefaultEncoding("utf-8");
      javaMailSender.setHost(host);
      javaMailSender.setPort(port);
      javaMailSender.setProtocol(protocol);
      if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {

      } else {
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
      }
    }
    return javaMailSender;
  }
}
