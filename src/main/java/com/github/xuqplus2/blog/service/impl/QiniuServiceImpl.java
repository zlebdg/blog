package com.github.xuqplus2.blog.service.impl;

import com.github.xuqplus2.blog.service.QiniuService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 七牛sdk文档: https://developer.qiniu.com/kodo/sdk/1239/java#server-upload
 */
@Slf4j
@Service
public class QiniuServiceImpl implements QiniuService {

    @Value("${project.qiniu.access-key}")
    String accessKey;
    @Value("${project.qiniu.secret-key}")
    String secretKey;
    @Value("${project.qiniu.bucket}")
    String bucket;
    @Value("${project.qiniu.domain}")
    String domain;

    @Override
    public String upload(InputStream is) throws QiniuException {
        //构造一个带指定Region对象的配置类
        Configuration cfg = new Configuration();
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Response response = uploadManager.put(is, key, upToken, null, null);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        log.info("putRet=>{}, ", putRet);
        return putRet.key;
    }

    @Override
    public String download(String key) {
        String url = String.format("%s/%s", domain, key);
        Auth auth = Auth.create(accessKey, secretKey);
        String privateUrl = auth.privateDownloadUrl(url, 3600);
        log.info("privateUrl=>{}, ", privateUrl);
        return privateUrl;
    }
}
