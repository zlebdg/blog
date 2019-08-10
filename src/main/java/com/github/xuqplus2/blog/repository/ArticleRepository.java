package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article getById(Long id);
}
