package com.github.xuqplus2.javawebdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {

  @GetMapping
  public String a() {
    return "ok";
  }
}
