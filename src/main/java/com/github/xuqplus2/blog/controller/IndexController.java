package com.github.xuqplus2.blog.controller;

import com.github.xuqplus2.blog.repository.OAuthCallbackAddressRepository;
import com.github.xuqplus2.blog.service.EncryptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequestMapping
@RestController
public class IndexController {

    @Autowired
    OAuthCallbackAddressRepository callbackAddressRepository;
    @Autowired
    EncryptService encryptService;

    @Value("${project.web.index}")
    String index;

    @GetMapping
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referer = request.getHeader("Referer");
        if (!StringUtils.isEmpty(referer)) {
            response.sendRedirect(String.format("%s#/oauth/callbackPage", referer));
            return;
        }
        response.sendRedirect(index);
    }
}
