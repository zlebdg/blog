package com.github.xuqplus2.blog.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@Entity
public class ArticleComment extends BasicDomain {
    @Id
    private Long id;

    @ManyToOne
    private User author;
    private AnonymousUser anonymousAuthor;
    @ManyToOne
    private Article article;
    @ManyToOne
    private ArticleComment replyTo;
    @OneToOne
    private AppText text;
    private String comment;
}
