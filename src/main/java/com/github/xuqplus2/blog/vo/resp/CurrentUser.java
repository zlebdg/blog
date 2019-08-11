package com.github.xuqplus2.blog.vo.resp;

import com.github.xuqplus2.blog.vo.VO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class CurrentUser extends VO {

    private static final String APP = "app";

    private String username; // 唯一的id
    private String nickname; // 显示的名字
    private String avatar;   // 头像地址
    private String appId;
    private Boolean authenticated;
    private Collection<String> authorities;
    private Collection<String> roles;

    public CurrentUser() {
    }
}
