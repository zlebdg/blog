package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.ArticleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleInfoRepository extends JpaRepository<ArticleInfo, Long> {
    ArticleInfo getById(Long id);
}
