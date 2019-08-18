package com.github.xuqplus2.blog.domain;

import com.github.xuqplus2.blog.vo.req.ArticleCommentReq;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class ArticleComment extends BasicDomain {

    // 大于此长度用 longtext 保存
    public static final int MIN_LOB_COMMENT_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;
    @ManyToOne
    private AnonymousUser anonymousAuthor;
    @ManyToOne
    private Article article;
    @ManyToOne
    private ArticleComment replyTo;
    @OneToOne
    private AppText appText;
    private String text;
    private boolean hasReplies;
    private String parseType;
    private String hash;

    public ArticleComment(ArticleCommentReq req) {
        this.hash = req.getHash();
        this.parseType = req.getParseType();
        if (req.getText().length() >= MIN_LOB_COMMENT_LENGTH) {
            this.text = req.getText();
        } else {
            AppText appText = new AppText();
            appText.setText(req.getText());
            this.appText = appText;
        }
    }

    public String getAuthorId() {
        if (null != this.author)
            return this.author.getId();
        if (null != this.anonymousAuthor)
            return this.anonymousAuthor.getId();
        return null;
    }

    public String getAuthorUsername() {
        if (null != this.author)
            return this.author.getUsername();
        if (null != this.anonymousAuthor)
            return this.anonymousAuthor.getUsername();
        return null;
    }

    public String getAuthorNickname() {
        if (null != this.author)
            return this.author.getAppId();
        return null;
    }

    public String getAuthorAppId() {
        if (null != this.author)
            return this.author.getAppId();
        return null;
    }

    public String getAuthorAvatar() {
        if (null != this.author)
            return this.author.getAvatar();
        return null;
    }

    public Long getReplyToId() {
        if (null != this.replyTo)
            return this.replyTo.getId();
        return null;
    }

    public String getReplyToAuthorId() {
        if (null != this.replyTo)
            return this.replyTo.getAuthorId();
        return null;
    }

    public String getReplyToAuthorUsername() {
        if (null != this.replyTo)
            return this.replyTo.getAuthorUsername();
        return null;
    }

    public String getText() {
        if (null == text && null != appText) {
            return appText.getText();
        }
        return text;
    }
}
