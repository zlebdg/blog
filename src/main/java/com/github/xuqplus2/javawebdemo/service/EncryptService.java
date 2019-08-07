package com.github.xuqplus2.javawebdemo.service;

public interface EncryptService {

    String md5(String input);

    String sha256(String input);

    String sha256Md5(String input);
}
