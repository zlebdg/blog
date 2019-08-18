package com.github.xuqplus2.blog.vo.resp;

import com.github.xuqplus2.blog.domain.ArticleComment;
import com.github.xuqplus2.blog.repository.ArticleCommentRepository;
import com.github.xuqplus2.blog.vo.VO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ArticleCommentResp extends VO {

    private Long id;
    private Long articleId;
    private String authorId;
    private String authorUsername;
    private String authorNickname;
    private String authorAppId;
    private String authorAvatar;
    private Long replyToId;                 // 回复的评论的id
    private String replyToAuthorId;         // 回复的评论的作者id
    private String replyToAuthorUsername;   // 回复的评论的作者名
    private String text;
    private Page<ArticleCommentResp> replies;
    private String parseType;
    private String hash;

    public ArticleCommentResp(ArticleComment articleComment, ArticleCommentRepository repository) {
        this.id = articleComment.getId();
        this.authorId = articleComment.getAuthorId();
        this.authorUsername = articleComment.getAuthorUsername();
        this.authorNickname = articleComment.getAuthorNickname();
        this.authorAppId = articleComment.getAuthorAppId();
        this.authorAvatar = articleComment.getAuthorAvatar();
        this.replyToId = articleComment.getReplyToId();
        this.replyToAuthorId = articleComment.getReplyToAuthorId();
        this.replyToAuthorUsername = articleComment.getReplyToAuthorUsername();
        this.text = articleComment.getText();
        if (articleComment.isHasReplies()) {
            List<ArticleCommentResp> collect = repository.findAllByReplyTo(articleComment).stream().map(r -> {
                return new ArticleCommentResp(r, repository);
            }).collect(Collectors.toList());
            this.replies = new PageImpl(collect);
        }

        this.parseType = articleComment.getParseType();
        this.hash = articleComment.getHash();
    }
}
