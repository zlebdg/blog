package com.github.xuqplus2.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.xuqplus2.blog.vo.resp.CurrentUser;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User extends BasicDomain {

    public static final long USER_INFO_EXPIRED_MILLS = 1000L * 60 * 10;

    @Id
    private String id; // username@appId
    private String username;
    private String nickname;
    private String appId;
    private String avatar;

    public User() {
    }

    public User(CurrentUser currentUser) {
        this.id = currentUser.getUserId();
        this.username = currentUser.getUsername();
        this.nickname = currentUser.getNickname();
        this.appId = currentUser.getAppId();
        this.avatar = currentUser.getAvatar();
    }

    public void update(CurrentUser currentUser) {
        this.nickname = currentUser.getNickname();
        this.appId = currentUser.getAppId();
        this.avatar = currentUser.getAvatar();
    }
}
