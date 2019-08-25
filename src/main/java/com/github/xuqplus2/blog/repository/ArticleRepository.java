package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article getById(Long id);

    boolean existsByHash(String hash);

    Page<Article> findAllByAuthorId(String userId, Pageable pageable);

    @Query("select a.id, a.title from Article as a where a.author.id = ?1")
    List<Object[]> findAllByAuthorIdPageable(String userId, Pageable pageable);
}
