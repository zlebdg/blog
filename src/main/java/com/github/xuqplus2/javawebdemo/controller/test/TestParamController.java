package com.github.xuqplus2.javawebdemo.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("test/param")
public class TestParamController {

  @GetMapping
  public String a(String[] aaa, String... bbb) {
    return String.format("aaa->%s, bbb->%s", Arrays.asList(aaa), Arrays.asList(bbb));
  }
}
