package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.BlogApplicationTests;
import com.github.xuqplus2.blog.domain.Article;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
            article.setText("hello, 世界.");
            articleRepository.save(article);
        }

        Article one = articleRepository.getOne(1L);
        logger.info("one=>{}", one);

        logger.info(new String(one.getText().getBytes("iso8859-1")));
    }
}
