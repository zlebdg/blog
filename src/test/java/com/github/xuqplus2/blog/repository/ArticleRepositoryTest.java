package com.github.xuqplus2.blog.repository;

import com.alibaba.fastjson.JSON;
import com.github.xuqplus2.blog.BlogApplicationTests;
import com.github.xuqplus2.blog.domain.Article;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

public class ArticleRepositoryTest extends BlogApplicationTests {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    @Transactional
    public void a() throws UnsupportedEncodingException {
        if (!articleRepository.existsById(1L)) {
            Article article = new Article();
//            article.setText("hello, 世界.");
            articleRepository.save(article);
        }

        Article one = articleRepository.getOne(1L);
        logger.info("one=>{}", one);

//        logger.info(new String(one.getText().getBytes("iso8859-1")));
    }

    @Test
    public void b() {

        Sort sort = JpaSort.by(Sort.Direction.DESC, "createAt");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Page<Article> page = articleRepository.findAll(PageRequest.of(0, 10));

        logger.info("page=>{}", JSON.toJSONString(page));
    }
}
