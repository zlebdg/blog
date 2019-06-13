package com.github.xuqplus2.javawebdemo.controller.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("test/fetch")
public class TestFetchController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestFetchController.class);

  @GetMapping("textOrJson")
  public Object textOrJson() {
    return String.format("{\"textOrJson\":\"%s\"}", "hello, 世界.");
  }

  @PostMapping("withParam")
  public Object withParam(@RequestBody Map body) {
    LOGGER.info("body={}", body);
    return body;
  }
}
