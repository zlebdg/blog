package com.github.xuqplus2.javawebdemo.controller;

import com.github.xuqplus2.javawebdemo.domain.oauth.OAuthCallbackAddress;
import com.github.xuqplus2.javawebdemo.repository.OAuthCallbackAddressRepository;
import com.github.xuqplus2.javawebdemo.service.EncryptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping
@RestController
public class IndexController {

    @Autowired
    OAuthCallbackAddressRepository callbackAddressRepository;
    @Autowired
    EncryptService encryptService;

    @GetMapping("/")
    public Object index(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider a;
        return String.format("授权信息: 名称=%s, 信息=%s, 凭据=%s, 权限=%s, ",
                authentication.getName(),
                authentication.getPrincipal(),
                authentication.getCredentials(),
                authentication.getAuthorities());
    }
}
