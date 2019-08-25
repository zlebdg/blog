package com.github.xuqplus2.blog.controller.article;

import com.github.xuqplus2.blog.BlogApplicationTests;
import com.github.xuqplus2.blog.repository.ArticleRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ArticleControllerTest extends BlogApplicationTests {

    @Autowired
    ArticleController controller;
    @Autowired
    ArticleRepository repository;

    @Test
    public void query() {

        ResponseEntity query = controller.query("root@app", 0, 10);

        ResponseEntity query2 = controller.query(null, 0, 10);

        logger.info("query=>{}", query);

        logger.info("query2=>{}", query2);
    }

    @Test
    public void findAllByAuthorIdPageable() {

        int page = 0, size = 10;

        Sort sort = JpaSort.by(Sort.Direction.DESC, "createAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        List<Object[]> articles = repository.findAllByAuthorIdPageable("root@app", pageRequest);

        logger.info("articles=>{}", articles);
    }
}