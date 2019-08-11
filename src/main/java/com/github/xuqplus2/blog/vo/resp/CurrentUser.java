package com.github.xuqplus2.blog.vo.resp;

import com.github.xuqplus2.blog.vo.VO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class CurrentUser extends VO {

    private String username; // 唯一的id
    private String nickname; // 显示的名字
    private String avatar;   // 头像地址
    private String appId;
    private Boolean authenticated;
    private Collection<String> authorities = new LinkedHashSet();
    private Collection<String> roles = new LinkedHashSet();

    public CurrentUser() {
    }

    public CurrentUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
    }
}
