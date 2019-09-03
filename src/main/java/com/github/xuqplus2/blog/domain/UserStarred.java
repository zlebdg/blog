package com.github.xuqplus2.blog.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
public class UserStarred implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private ArticleInfo articleInfo;

    public UserStarred(ArticleInfo info, User user) {
        this.articleInfo = info;
        this.user = user;
    }
}
