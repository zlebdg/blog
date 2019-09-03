package com.github.xuqplus2.blog.vo.resp;

import com.github.xuqplus2.blog.domain.ArticleInfo;
import com.github.xuqplus2.blog.vo.VO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleInfoResp extends VO {

    private Long id;
    private int like; // 点赞
    private int dislike; // 反对
    private int star; // 星星
    private int comment; // 评论
    private int trans; // 转发
    private int read; // 阅读

    private String userId;
    private Boolean liked;
    private Boolean disliked;
    private Boolean starred;

    public ArticleInfoResp(ArticleInfo info, String userId, Boolean liked, Boolean disliked, Boolean starred) {
        this.id = info.getId();
        this.like = info.getLike();
        this.dislike = info.getDislike();
        this.star = info.getStar();
        this.comment = info.getStar();
        this.trans = info.getTrans();
        this.read = info.getRead();
        this.userId = userId;
        this.liked = liked;
        this.disliked = disliked;
        this.starred = starred;
    }
}
