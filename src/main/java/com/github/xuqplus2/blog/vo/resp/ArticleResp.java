package com.github.xuqplus2.blog.vo.resp;

import com.github.xuqplus2.blog.domain.Article;
import com.github.xuqplus2.blog.domain.ArticleInfo;
import com.github.xuqplus2.blog.domain.User;
import com.github.xuqplus2.blog.vo.VO;
import lombok.Data;

@Data
public class ArticleResp extends VO {

    private Long id;
    private Long createAt;
    private Long updateAt;
    private String title;
    private String text;
    private String preview;
    private String parseType;
    private String hash;
    private User author;
    private ArticleInfo articleInfo;

    public ArticleResp(Article article) {
        this.id = article.getId();
        this.createAt = article.getCreateAt();
        this.updateAt = article.getUpdateAt();
        this.title = article.getTitle();
        this.text = null != article.getAppText() ? article.getAppText().getText() : null;
        this.preview = article.getPreview();
        this.parseType = article.getParseType();
        this.hash = article.getHash();
        this.author = article.getAuthor();
        this.articleInfo = article.getArticleInfo();
    }
}
