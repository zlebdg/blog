package com.github.xuqplus2.javawebdemo.controller.oauth.callback;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.github.xuqplus2.javawebdemo.config.AlipaysApps;
import com.github.xuqplus2.javawebdemo.controller.oauth.token.GithubAccessToken;
import com.github.xuqplus2.javawebdemo.domain.GithubUserInfo;
import com.github.xuqplus2.javawebdemo.repository.GithubUserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("oauth/callback")
@Slf4j
public class OAuthCallbackController {

  @Value("${project.oauth.github.clientId}")
  String githubClientId;
  @Value("${project.oauth.github.clientSecret}")
  String githubClientSecret;

  @Autowired
  GithubUserInfoRepository githubUserInfoRepository;
  @Autowired
  AlipaysApps.AlipaysApp app;

  @GetMapping("github")
  public String github(String code, String state) throws IOException {
    log.info("code=>{}, state=>{}", code, state);
    OkHttpClient okHttpClient = new OkHttpClient();
    Response response = okHttpClient.newCall(new Request.Builder()
            .url(String.format("https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s", githubClientId, githubClientSecret, code))
            .addHeader("accept", "application/json")
            .build())
            .execute();
    GithubAccessToken githubAccessToken = JSON.parseObject(response.body().string(), GithubAccessToken.class);
    log.info("githubAccessToken=>{}", githubAccessToken);

    Response response1 = okHttpClient.newCall(new Request.Builder()
            .url(String.format("https://api.github.com/user?access_token=%s", githubAccessToken.getAccess_token()))
            .addHeader("accept", "application/json")
            .build())
            .execute();
    GithubUserInfo githubUserInfo = JSON.parseObject(response1.body().string(), GithubUserInfo.class);
    githubUserInfoRepository.save(githubUserInfo);
    log.info("githubUserInfo=>{}", githubUserInfo);
    return githubUserInfo.toString();
  }

  @GetMapping("alipay")
  public ModelAndView callback(String app_id, String scope, String auth_code, ModelAndView mav) {
    log.info("callback, app_id={}, scope={}, auth_code={}, ", app_id, scope, auth_code);
    AlipayClient alipayClient = new DefaultAlipayClient(
            "https://openapi.alipay.com/gateway.do",
            app.appId,
            app.privateKey,
            "json",
            "UTF-8",
            app.publicKey,
            "RSA2");
    AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
    request.setGrantType("authorization_code");
    request.setCode(auth_code);
    AlipaySystemOauthTokenResponse response = null;
    try {
      response = alipayClient.execute(request);
    } catch (AlipayApiException e) {
      log.info("auth_code={}, 换取userId失败, e.message={}, e.getErrMsg={}", auth_code, e.getMessage(), e.getErrMsg());
    }
    if (response.isSuccess()) {
      String body = response.getBody();
      log.info("body=>{}", body);
    }
    return mav;
  }
}
