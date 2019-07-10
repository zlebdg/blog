package com.github.xuqplus2.javawebdemo.config;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthApp {

  /**
   * alipay app
   */
  private static final AlipayApp ALIPAY_APP = new AlipayApp(
          "https://openapi.alipay.com/gateway.do",
          "2019071065826094",
          "浦江张学友_dev",
          // 支付宝开放平台的公钥
          "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",
          "http://dev.local:20000/",
          "http://dev.local:20000/oauth/callback/alipay/",
          "auth_base,auth_user",
          "RSA2",
          "UTF-8",
          "json");

  @Bean
  public AlipayApp alipaysApp() {
    return ALIPAY_APP;
  }

  @Data
  @Builder
  public static class AlipayApp {
    private String alipayGateway;
    private String appId;
    private String appName;
    private String alipayPublicKey;
    private String privateKey;
    private String domain;
    private String authCallbackUrl;
    private String scope;
    private String signType;
    private String charset;
    private String format;
  }

  /**
   * github oauth app
   */
  @Value("${project.oauth.github.clientId}")
  String clientId;
  @Value("${project.oauth.github.clientSecret}")
  String clientSecret;
  @Value("${project.oauth.github.redirectUri}")
  String redirectUri;
  @Value("${project.oauth.github.scope}")
  String scope;

  @Bean
  public GithubApp githubApp() {
    return new GithubApp(clientId, clientSecret, redirectUri, scope);
  }

  @Data
  @Builder
  public static class GithubApp {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope;
  }
}
