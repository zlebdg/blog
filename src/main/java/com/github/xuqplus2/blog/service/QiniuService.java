package com.github.xuqplus2.blog.service;

import com.qiniu.common.QiniuException;

import java.io.InputStream;

public interface QiniuService {

    String upload(InputStream is) throws QiniuException;

    String download(String key);
}
