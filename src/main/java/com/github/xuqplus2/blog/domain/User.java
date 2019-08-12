package com.github.xuqplus2.blog.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class User extends BasicDomain {
    @Id
    private String id; // username@appId
    private String username;
    private String nickname;
    private String appId;
    private String avatar;
}
