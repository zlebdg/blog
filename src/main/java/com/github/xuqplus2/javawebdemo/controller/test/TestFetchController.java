package com.github.xuqplus2.javawebdemo.controller.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("test/fetch")
public class TestFetchController {

  private static final String uploadFileDir = String.format("uploadFileDir%s", File.separator);

  static {
    if (!new File(uploadFileDir).exists()) {
      new File(uploadFileDir).mkdirs();
    }
  }

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

  @PostMapping("uploadFile")
  public String uploadFile(MultipartFile file) throws IOException {
    LOGGER.info("file.getOriginalFilename()={}", file.getOriginalFilename());
    file.transferTo(new File(uploadFileDir + file.getOriginalFilename()).toPath());
    return String.format("{\"filename\":\"%s\",\"status\":\"ok\"}", file.getOriginalFilename());
  }

  @PostMapping("uploadFiles")
  public Object uploadFiles(MultipartFile[] files) throws IOException {
    List<String> r = new ArrayList<>();
    for (MultipartFile file : files) {
      r.add(uploadFile(file));
    }
    return r;
  }

  @PostMapping("headers")
  public Object headers(HttpServletRequest request) {
    Enumeration<String> headerNames = request.getHeaderNames();
    List<String> r = new ArrayList();
    while (headerNames.hasMoreElements()) {
      String name = headerNames.nextElement();
      String header = String.format("%s: %s", name, request.getHeader(name));
      LOGGER.info(header);
      r.add(header);
    }
    return r;
  }
}
