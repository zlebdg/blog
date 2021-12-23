package com.github.xuqplus2.blog.controller.test;

import com.github.xuqplus2.blog.util.RandomUtil;
import com.github.xuqplus2.blog.vo.resp.BasicResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("blog/test/dva")
public class TestDvaController {

    @GetMapping("query")
    public ResponseEntity query() throws InterruptedException {
        Thread.sleep(RandomUtil.nextInt(5000));
        return BasicResp.ok(RandomUtil.nextInt(5000));
    }
}
