package com.github.xuqplus2.javawebdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipaysApps {

  private static final AlipaysApp app = new AlipaysApp(
          "2019071065826094",
          "浦江张学友_dev",
          "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",
          "http://192.168.0.107:20000/",
          "http://192.168.0.107:20000/oauth/callback/alipay/");

  @Bean
  public AlipaysApp app() {
    return app;
  }

  public static class AlipaysApp {
    public String appId;
    public String appName;
    public String publicKey;
    public String privateKey;
    public String domain;
    public String authCallbackUrl;

    public AlipaysApp(String appId, String appName, String publicKey, String privateKey, String domain, String authCallbackUrl) {
      this.appId = appId;
      this.appName = appName;
      this.publicKey = publicKey;
      this.privateKey = privateKey;
      this.domain = domain;
      this.authCallbackUrl = authCallbackUrl;
    }
  }
}
