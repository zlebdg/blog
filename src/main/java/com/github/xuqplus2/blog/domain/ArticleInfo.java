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
    @Column(name = "like_")
    private int like; // 点赞
    private int dislike; // 反对
    private int star; // 星星
    @Column(name = "comment_")
    private int comment; // 评论
    private int trans; // 转发
    @Column(name = "read_")
    private int read; // 阅读
}