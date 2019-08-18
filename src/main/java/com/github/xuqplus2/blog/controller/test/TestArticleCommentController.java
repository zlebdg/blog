package com.github.xuqplus2.blog.controller.test;

import com.github.xuqplus2.blog.controller.article.ArticleCommentController;
import com.github.xuqplus2.blog.vo.req.ArticleCommentReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("test/articleComment")
public class TestArticleCommentController {

    @Autowired
    ArticleCommentController articleCommentController;

    @GetMapping("post")
    public ResponseEntity post(@Valid ArticleCommentReq req, BindingResult bindingResult) throws InterruptedException {
        ResponseEntity post = articleCommentController.post(req, bindingResult);
        return post;
    }

    @GetMapping("comments")
    public ResponseEntity comments(Long id, Integer page, Integer size) {
        ResponseEntity comments = articleCommentController.comments(id, page, size);
        return comments;
    }
}
