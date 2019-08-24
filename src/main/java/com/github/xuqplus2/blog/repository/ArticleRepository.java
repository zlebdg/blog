package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article getById(Long id);

    boolean existsByHash(String hash);

    Page<Article> findAllByAuthorId(String userId, Pageable pageable);

    List<Article> findAllByAuthorId(String userId);
}
