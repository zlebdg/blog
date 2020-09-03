package com.github.xuqplus2.blog.service.impl;

import com.github.xuqplus2.blog.service.QiniuService;
import com.qiniu.common.QiniuException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class QiniuServiceImplTest {

    @Autowired
    QiniuService qiniuService;

    @Test
    public void upload() throws QiniuException {
        byte[] bytes = "qiniu test 123 测试".getBytes();
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);

        String key = qiniuService.upload(is);
    }

    @Test
    public void download() throws UnsupportedEncodingException {
        String url = qiniuService.download("Fu9GvHt0afnDL2caLDY8njIXBsdn");
    }
}