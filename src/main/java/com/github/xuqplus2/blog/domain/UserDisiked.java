package com.github.xuqplus2.blog.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserDisiked extends BasicDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private ArticleInfo articleInfo;
}
