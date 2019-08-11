package com.github.xuqplus2.blog.controller.public_;

import com.github.xuqplus2.blog.controller.article.ArticleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class APublicController {

    @Autowired
    ArticleController articleController;

    @GetMapping("article")
    public ResponseEntity query(Long id) {
        return articleController.query(id);
    }
}
