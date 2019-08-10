package com.github.xuqplus2.blog.domain;

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
    @Lob // longtext类型, 保存ok, 查询ok
    // @Column(columnDefinition = "longblob") // 保存ok, 如果不同时使用 @Lob 注解, 查询可能乱码
    // todo, 存储表情报错
    private String text;
    @Lob
    private String preview; // 预览内容, 从text中提取
    @NotEmpty
    private String parseType; // 解析方式, 应当与text同时提交
    @Column(unique = true)
    private String hash; // userId + hash(标题 + 内容), 可用于防止重复提交

    public void set(Article article) {
        this.title = article.getTitle();
        this.text = article.getText();
        this.parseType = article.getParseType();
        this.hash = article.getHash();
    }
}
