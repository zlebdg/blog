package com.github.xuqplus2.blog.vo.req;

import com.github.xuqplus2.blog.vo.VO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ArticleCommentReq extends VO {

    @NotEmpty
    private Long articleId;
    private Long replyToId;
    @NotEmpty
    private String text;
    @NotEmpty
    private String parseType;
    @NotEmpty
    private String hash;
}
