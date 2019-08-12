package com.github.xuqplus2.blog.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ArticleInfo extends BasicDomain {
    @Id
    private Long id;
    private Integer oppose; // 反对
    private Integer star; // 点赞
    private Integer comment; // 评论
    private Integer trans; // 转发
    private Integer collect; // 收藏
    @Column(name = "read_")
    private Integer read; // 阅读
}
