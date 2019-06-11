package com.github.xuqplus2.javawebdemo.controller;

import com.github.xuqplus2.javawebdemo.domain.User;
import com.github.xuqplus2.javawebdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {

  @Autowired UserRepository userRepository;

  @GetMapping
  public String a() {
    return "ok";
  }

  @GetMapping("user")
  public User user(Long id) {
    return userRepository.getOne(id);
  }

  @PostMapping("user")
  public User user(Long id, String name) {
    User user = new User().setId(id).setName(name);
    return userRepository.save(user);
  }
}
