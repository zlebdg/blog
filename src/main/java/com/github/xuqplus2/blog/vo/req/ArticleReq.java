package com.github.xuqplus2.blog.vo.req;

import com.github.xuqplus2.blog.domain.AppText;
import com.github.xuqplus2.blog.vo.VO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Data
public class ArticleReq extends VO {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    @NotEmpty
    private String preview;
    @NotEmpty
    private String parseType;
    @NotEmpty
    private String hash;
}
