package com.github.xuqplus2.blog.vo.req;

import com.github.xuqplus2.blog.vo.VO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ArticleReq extends VO {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    private String preview;
    @NotEmpty
    private String parseType;
    @NotEmpty
    private String hash;
}
