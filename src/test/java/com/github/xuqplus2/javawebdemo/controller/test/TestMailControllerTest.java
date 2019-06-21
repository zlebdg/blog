package com.github.xuqplus2.javawebdemo.controller.test;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMailControllerTest {

  @Autowired TestMailController testMailController;

  @Value("${git.commit}")
  String gitCommit;

  /** 该测试用例可能因为标题和邮件内容被退信 */
  @Test
  public void sendSimpleMailMessage() {
    String subject = String.format("工作报告-%s", DateTime.now().toString("yyyy-MM-dd HH:mm:ss.sss"));
    String text =
        String.format(
            "以下是工作报告内容: gitCommit=%s, DateTime=%s, text, text, 123, abc...",
            gitCommit, DateTime.now().toString("yyyy-MM-dd HH:mm:ss.sss"));
    String from = "xuqplus@163.com";
    String[] to = {"445172495@qq.com"};
    String r = testMailController.sendSimpleMailMessage(subject, text, from, to);

    Assert.assertEquals("ok", r);
  }
}
