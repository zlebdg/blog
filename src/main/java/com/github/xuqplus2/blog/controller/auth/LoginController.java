package com.github.xuqplus2.blog.controller.auth;

import com.github.xuqplus2.blog.vo.resp.BasicResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("blog/auth")
public class LoginController {

    @Value("${security.oauth2.logout.uri}")
    String logoutUri;

    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

    @RequestMapping(value = "currentUser", method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return BasicResp.ok(authentication.getPrincipal());
    }

    @Transactional
    @RequestMapping(value = "logout", method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity logout() {
        Map<String, String> data = new LinkedHashMap<>(8);
        data.put("logoutUri", logoutUri);
        OAuth2AccessToken accessToken = oAuth2ClientContext.getAccessToken();
        if (null != accessToken) {
            data.put("accessToken", accessToken.getValue());
            data.put("refreshToken", accessToken.getRefreshToken().getValue());
        }
        // 删除accessToken
        oAuth2ClientContext.setAccessToken(null);
        // 清除状态
        SecurityContextHolder.clearContext();
        return BasicResp.ok(data);
    }
}
