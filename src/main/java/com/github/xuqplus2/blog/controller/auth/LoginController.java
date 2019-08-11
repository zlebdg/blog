package com.github.xuqplus2.blog.controller.auth;

import com.github.xuqplus2.blog.vo.resp.BasicResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Slf4j
@RestController
@RequestMapping("auth")
public class LoginController {

    @RequestMapping(value = "currentUser", method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return BasicResp.ok(authentication.getPrincipal());
    }

    @Transactional
    @RequestMapping(value = "logout", method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity logout() {
        // 清除状态
        SecurityContextHolder.clearContext();
        return BasicResp.ok();
    }
}
