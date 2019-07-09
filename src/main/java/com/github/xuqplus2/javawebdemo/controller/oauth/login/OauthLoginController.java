package com.github.xuqplus2.javawebdemo.controller.oauth.login;

import com.github.xuqplus2.javawebdemo.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("oauth/login")
public class OauthLoginController {

  final String TEMPLATE_AUTHORIZE_URL_GITHUB = "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&scope=%s&state=%s";
  final int STATE_LENGTH = 20;

  @Value("${project.oauth.github.clientId}")
  String githubClientId;
  @Value("${project.oauth.github.clientSecret}")
  String githubClientSecret;
  @Value("${project.oauth.github.redirectUri}")
  String githubRedirectUri;
  String scope = "user";

  @GetMapping("github")
  public ModelAndView github(ModelAndView mav) {
    mav.setViewName(String.format(String.format("redirect:%s", TEMPLATE_AUTHORIZE_URL_GITHUB), githubClientId, githubRedirectUri, scope, RandomUtil.numiric(STATE_LENGTH)));
    return mav;
  }
}
