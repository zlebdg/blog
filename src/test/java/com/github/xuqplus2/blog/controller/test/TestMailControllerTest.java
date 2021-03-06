package com.github.xuqplus2.blog.controller.test;

import com.github.xuqplus2.blog.BlogApplicationTests;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class TestMailControllerTest extends BlogApplicationTests {

    @Autowired
    TestMailController testMailController;

    @Value("${git.commit}")
    String gitCommit;

    /**
     * 该测试用例可能因为标题, 邮件内容, 发件频率...等等原因被退信
     * 所以最好到qq邮箱添加白名单
     */
    @Test
    public void sendSimpleMailMessage() {
        if (DEV.equals(profile)) {
            // 开发环境不测试邮件发送, 以减少发件频率
            return;
        }
        String subject = String.format("构建报告-%s", DateTime.now().toString("yyyy-MM-dd HH:mm:ss.sss"));
        String text =
                String.format(
                        "内容: gitCommit=%s, text, 123abc...", gitCommit);
        String[] to = {"445172495@qq.com"};
        String r = testMailController.sendSimpleMailMessage(subject, text, to);

        Assert.assertEquals("ok", r);
    }
}
