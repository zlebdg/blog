package com.github.xuqplus2.blog.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class AnonymousUser implements Serializable {

    @Id
    private String id;
    private String username;
    private String ip;
    private String v; // 版本号
}
