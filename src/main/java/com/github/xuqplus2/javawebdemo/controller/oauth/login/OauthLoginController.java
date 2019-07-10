package com.github.xuqplus2.javawebdemo.controller.oauth.login;

import com.github.xuqplus2.javawebdemo.config.AlipaysApps;
import com.github.xuqplus2.javawebdemo.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("oauth/login")
@Slf4j
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

  @Autowired
  AlipaysApps.AlipaysApp app;

  @GetMapping("github")
  public ModelAndView github(ModelAndView mav) {
    mav.setViewName(String.format(String.format("redirect:%s", TEMPLATE_AUTHORIZE_URL_GITHUB), githubClientId, githubRedirectUri, scope, RandomUtil.numiric(STATE_LENGTH)));
    return mav;
  }

  @GetMapping("alipay")
  public ModelAndView alipay(ModelAndView mav, String orderNo) throws UnsupportedEncodingException {
    String callback = URLEncoder.encode(app.authCallbackUrl + orderNo, "utf8");
    log.info("auth, orderNO={}, callback={}", orderNo, callback);
    mav.setViewName(String.format(
            "redirect:https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=%s&scope=auth_base&redirect_uri=%s",
            app.appId, callback));
    return mav;
  }
}
