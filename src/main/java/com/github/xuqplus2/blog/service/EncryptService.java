package com.github.xuqplus2.blog.service;

public interface EncryptService {

    String md5(String input);

    String sha256(String input);

    String sha256Md5(String input);
}
