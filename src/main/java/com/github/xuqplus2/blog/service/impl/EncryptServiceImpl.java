package com.github.xuqplus2.blog.service.impl;

import com.github.xuqplus2.blog.service.EncryptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
@Slf4j
public class EncryptServiceImpl implements EncryptService {

    private static MessageDigest SHA256 = null;
    private static MessageDigest MD5 = null;

    /* 如有异常直接向上传导使项目启动失败 */
    public EncryptServiceImpl() throws NoSuchAlgorithmException {
        SHA256 = MessageDigest.getInstance("SHA-256");
        MD5 = MessageDigest.getInstance("MD5");
    }

    @Override
    public String md5(String input) {
        return new String(Hex.encode(MD5.digest(input.getBytes())));
    }

    @Override
    public String sha256(String input) {
        return new String(Hex.encode(SHA256.digest(input.getBytes())));
    }

    /**
     * 不等价于 sha256(md5(input))
     *
     * @param input
     * @return
     */
    @Override
    public String sha256Md5(String input) {
        return new String(Hex.encode(MD5.digest(SHA256.digest(input.getBytes()))));
    }
}
