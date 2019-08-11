package com.github.xuqplus2.blog.config.kz;

import com.alibaba.fastjson.JSON;
import com.github.xuqplus2.blog.vo.resp.CurrentUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Map;

/**
 * 替换默认的<code>extractAuthentication</code>方法
 * 在oauth client获得更多的用户信息
 */
public class AppDefaultUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        Authentication authentication = super.extractAuthentication(map);
        if (map.containsKey("currentUser")) {
            CurrentUser principal = JSON.parseObject(JSON.toJSONString(map.get("currentUser")), CurrentUser.class);
            return new UsernamePasswordAuthenticationToken(principal, authentication.getCredentials(), authentication.getAuthorities());
        }
        return authentication;
    }
}
