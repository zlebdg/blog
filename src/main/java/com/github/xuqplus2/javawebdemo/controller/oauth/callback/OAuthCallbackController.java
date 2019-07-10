package com.github.xuqplus2.javawebdemo.controller.oauth.callback;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.github.xuqplus2.javawebdemo.config.OAuthApp;
import com.github.xuqplus2.javawebdemo.controller.oauth.token.GithubAccessToken;
import com.github.xuqplus2.javawebdemo.domain.AlipayUserInfo;
import com.github.xuqplus2.javawebdemo.domain.GithubUserInfo;
import com.github.xuqplus2.javawebdemo.repository.AlipayUserInfoRepository;
import com.github.xuqplus2.javawebdemo.repository.GithubUserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("oauth/callback")
@Slf4j
public class OAuthCallbackController {

  @Autowired
  GithubUserInfoRepository githubUserInfoRepository;
  @Autowired
  AlipayUserInfoRepository alipayUserInfoRepository;
  @Autowired
  OAuthApp.AlipayApp alipayApp;
  @Autowired
  OAuthApp.GithubApp githubApp;

  private static final String TEMPLATE_OAUTH_ACCESS_TOKEN_URI_GITHUB = "https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s";
  private static final String TEMPLATE_OAUTH_USER_INFO_URI_GITHUB = "https://api.github.com/user?access_token=%s";

  @GetMapping("github")
  public String github(String code, String state) throws IOException {
    log.info("code=>{}, state=>{}", code, state);
    OkHttpClient okHttpClient = new OkHttpClient();
    Response response = okHttpClient.newCall(new Request.Builder()
            .url(String.format(TEMPLATE_OAUTH_ACCESS_TOKEN_URI_GITHUB, githubApp.getClientId(), githubApp.getClientSecret(), code))
            .addHeader("accept", "application/json")
            .build())
            .execute();
    GithubAccessToken githubAccessToken = JSON.parseObject(response.body().string(), GithubAccessToken.class);
    log.info("githubAccessToken=>{}", githubAccessToken);

    Response response1 = okHttpClient.newCall(new Request.Builder()
            .url(String.format(TEMPLATE_OAUTH_USER_INFO_URI_GITHUB, githubAccessToken.getAccess_token()))
            .addHeader("accept", "application/json")
            .build())
            .execute();
    GithubUserInfo githubUserInfo = JSON.parseObject(response1.body().string(), GithubUserInfo.class);
    githubUserInfoRepository.save(githubUserInfo);
    log.info("githubUserInfo=>{}", githubUserInfo);
    return githubUserInfo.toString();
  }

  @GetMapping("alipay")
  public String alipay(String app_id, String scope, String auth_code, String state) {
    log.info("alipay callback, app_id={}, scope={}, auth_code={}, state={}", app_id, scope, auth_code, state);
    AlipayClient alipayClient = new DefaultAlipayClient(
            alipayApp.getAlipayGateway(),
            alipayApp.getAppId(),
            alipayApp.getPrivateKey(),
            alipayApp.getFormat(),
            alipayApp.getCharset(),
            alipayApp.getAlipayPublicKey(),
            alipayApp.getSignType());
    AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
    request.setGrantType("authorization_code");
    request.setCode(auth_code);
    try {
      AlipaySystemOauthTokenResponse alipayAccessToken = alipayClient.execute(request);
      if (null != alipayAccessToken && alipayAccessToken.isSuccess()) {
        log.info("alipayAccessToken=>{}", alipayAccessToken);

        AlipayUserInfoShareResponse alipayUserInfo = alipayClient.execute(new AlipayUserInfoShareRequest(), alipayAccessToken.getAccessToken());
        if (null != alipayUserInfo && alipayUserInfo.isSuccess()) {
          AlipayUserInfo alipayUserInfoLocal = new AlipayUserInfo();
          BeanUtils.copyProperties(alipayUserInfo, alipayUserInfoLocal);
          alipayUserInfoRepository.save(alipayUserInfoLocal);
          log.info("alipayUserInfoLocal=>{}", alipayUserInfoLocal);
          return alipayUserInfoLocal.toString();
        }
      }
    } catch (AlipayApiException e) {
      log.info("auth_code={}, 换取userId失败, e.message={}, e.getErrMsg={}", auth_code, e.getMessage(), e.getErrMsg());
    }
    throw new RuntimeException("open alipay 授权异常");
  }
}
