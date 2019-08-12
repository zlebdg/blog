package com.github.xuqplus2.blog.controller.public_;

import com.github.xuqplus2.blog.controller.article.ArticleController;
import com.github.xuqplus2.blog.domain.Article;
import com.github.xuqplus2.blog.repository.ArticleRepository;
import com.github.xuqplus2.blog.vo.resp.BasicResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class APublicController {

    @Autowired
    ArticleController articleController;
    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("article")
    public ResponseEntity query(Long id) {
        return articleController.query(id);
    }

    @GetMapping("article/query")
    public ResponseEntity query(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, JpaSort.by(Sort.Direction.DESC, "createAt"));
        Page<Article> all = articleRepository.findAll(pageRequest);
        return BasicResp.ok(all);
    }
}
