package com.github.xuqplus2.javawebdemo.controller;

import com.github.xuqplus2.javawebdemo.domain.User;
import com.github.xuqplus2.javawebdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {

  @Autowired
  UserRepository userRepository;

  @Value("${git.commit}")
  String gitCommit;

  @GetMapping("gitCommit")
  public Object gitCommit() {
    return String.format("{\"gitCommit\":\"%s\"}", gitCommit);
  }

  @GetMapping("user")
  public String user(Long id) {
    return userRepository.getOne(id).toString();
  }

  @PostMapping("user")
  public User user(Long id, String name) {
    User user = User.builder().id(id).name(name).build();
    return userRepository.save(user);
  }
}
