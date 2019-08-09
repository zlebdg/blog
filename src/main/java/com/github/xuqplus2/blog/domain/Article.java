package com.github.xuqplus2.blog.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Article extends BasicDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob // longtext类型, 保存ok, 查询ok
    @Column(columnDefinition = "longblob") // 保存ok, 如果不同时使用 @Lob 注解, 查询可能乱码
    private String text;
}
