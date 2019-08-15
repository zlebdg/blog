package com.github.xuqplus2.blog.controller.article;

import com.github.xuqplus2.blog.BlogApplicationTests;
import com.github.xuqplus2.blog.vo.req.ArticleCommentReq;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class ArticleCommentControllerTest extends BlogApplicationTests {

    @Autowired
    ArticleCommentController controller;

    @Test
    public void post() {
        ArticleCommentReq req = new ArticleCommentReq();
        req.setArticleId(1L);
        req.setComment("hahaha");
        req.setParseType("0.0.1");
        req.setHash("haha");

        ResponseEntity post = controller.post(req, null);

        logger.info("post=>{}", post);
    }
}