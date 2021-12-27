package com.github.xuqplus2.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

@Slf4j
@RequestMapping
@RestController
public class IndexController {

    @Value("${project.web.index}")
    String index;

    @GetMapping
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referer = request.getHeader("referer");
        if (!StringUtils.isEmpty(referer)) {
            response.sendRedirect(String.format("%s#/oauth/callbackPage", referer));
            return;
        }
        if (index.contains("ngrok.io")) {
            URL url = new URL(index);
            response.sendRedirect(String.format("%s", url.getPath()));
            return;
        }
        response.sendRedirect(index);
    }
}
