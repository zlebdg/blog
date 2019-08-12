package com.github.xuqplus2.blog.domain;

import com.github.xuqplus2.blog.vo.req.ArticleReq;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
public class Article extends BasicDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @OneToOne(fetch = FetchType.LAZY)
    private AppText appText;
    @Lob
    private String preview; // 预览内容, 从text中提取
    @NotEmpty
    private String parseType; // 解析方式, 应当与text同时提交
    @Column(unique = true)
    private String hash; // userId + hash(标题 + 内容), 可用于防止重复提交

    @ManyToOne
    private User author;
    @OneToOne
    private ArticleInfo articleInfo;

    public Article() {
    }

    public Article(ArticleReq article) {
        this.title = article.getTitle();
        this.preview = article.getPreview();
        this.parseType = article.getParseType();
        this.hash = article.getHash();
    }

    public void set(ArticleReq articleReq) {
        this.title = articleReq.getTitle();
        this.parseType = articleReq.getParseType();
        this.hash = articleReq.getHash();
    }
}
