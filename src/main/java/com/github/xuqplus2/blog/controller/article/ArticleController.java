package com.github.xuqplus2.blog.controller.article;

import com.github.xuqplus2.blog.domain.Article;
import com.github.xuqplus2.blog.repository.ArticleRepository;
import com.github.xuqplus2.blog.vo.resp.BasicResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("article")
@RestController
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping
    public ResponseEntity a(Long id) {
        Article article = articleRepository.getById(id);
        if (null != article)
            return BasicResp.ok(article);
        throw new RuntimeException("文章不存在");
    }

    @PostMapping
    public ResponseEntity a(Article article) {
        if (null == article.getId()) {
            articleRepository.save(article);
            return BasicResp.ok(article.getId());
        } else {
            Article saved = articleRepository.getById(article.getId());
            saved.set(article);
            articleRepository.save(saved);
            return BasicResp.ok();
        }
    }
}
