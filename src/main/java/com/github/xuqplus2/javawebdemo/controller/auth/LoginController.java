package com.github.xuqplus2.javawebdemo.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("auth")
public class LoginController {

    @RequestMapping("currentUser")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getHeader("Referer");
        log.info("referer=>{}", referer);
    }
}
