package com.github.xuqplus2.blog.controller;

import com.github.xuqplus2.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("blog")
public class AController {

    @Autowired
    UserRepository userRepository;

    @Value("${git.commit}")
    String gitCommit;

    @GetMapping("gitCommit")
    public Object gitCommit() {
        return String.format("{\"gitCommit\":\"%s\"}", gitCommit);
    }
}
