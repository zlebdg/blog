package com.github.xuqplus2.blog.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class UserLiked extends BasicDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private ArticleInfo articleInfo;

    public UserLiked(ArticleInfo info, User user) {
        this.articleInfo = info;
        this.user = user;
    }
}
