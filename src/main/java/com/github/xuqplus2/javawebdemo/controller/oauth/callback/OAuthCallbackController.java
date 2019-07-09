package com.github.xuqplus2.javawebdemo.controller.oauth.callback;

import com.alibaba.fastjson.JSON;
import com.github.xuqplus2.javawebdemo.controller.oauth.token.GithubAccessToken;
import com.github.xuqplus2.javawebdemo.domain.GithubUserInfo;
import com.github.xuqplus2.javawebdemo.repository.GithubUserInfoRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("oauth/callback")
public class OAuthCallbackController {

  private final Logger logger = LoggerFactory.getLogger(OAuthCallbackController.class);

  @Value("${project.oauth.github.clientId}")
  String githubClientId;
  @Value("${project.oauth.github.clientSecret}")
  String githubClientSecret;

  @Autowired
  GithubUserInfoRepository githubUserInfoRepository;

  @GetMapping("github")
  public String github(String code, String state) throws IOException {
    logger.info("code=>{}, state=>{}", code, state);
    OkHttpClient okHttpClient = new OkHttpClient();
    Response response = okHttpClient.newCall(new Request.Builder()
            .url(String.format("https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s", githubClientId, githubClientSecret, code))
            .addHeader("accept", "application/json")
            .build())
            .execute();
    GithubAccessToken githubAccessToken = JSON.parseObject(response.body().string(), GithubAccessToken.class);
    logger.info("githubAccessToken=>{}", githubAccessToken);

    Response response1 = okHttpClient.newCall(new Request.Builder()
            .url(String.format("https://api.github.com/user?access_token=%s", githubAccessToken.getAccess_token()))
            .addHeader("accept", "application/json")
            .build())
            .execute();
    GithubUserInfo githubUserInfo = JSON.parseObject(response1.body().string(), GithubUserInfo.class);
    githubUserInfoRepository.save(githubUserInfo);
    logger.info("githubUserInfo=>{}", githubUserInfo);
    return githubUserInfo.toString();
  }
}
