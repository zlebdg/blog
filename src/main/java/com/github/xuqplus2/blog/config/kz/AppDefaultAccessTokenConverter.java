package com.github.xuqplus2.blog.config.kz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

@Slf4j
public class AppDefaultAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        log.info("authentication=>{}", authentication);
        return authentication;
    }
}
