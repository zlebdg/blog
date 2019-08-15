package com.github.xuqplus2.blog.util;

public class AppNotLoginException extends Exception {

    public AppNotLoginException() {
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (null != message)
            return message;
        return "未登录";
    }
}
