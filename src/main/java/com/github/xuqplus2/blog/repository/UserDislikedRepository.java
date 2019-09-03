package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.UserDisliked;
import com.github.xuqplus2.blog.domain.UserLiked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDislikedRepository extends JpaRepository<UserDisliked, Long> {

    UserDisliked getByArticleInfoIdAndUserId(Long articleId, String userId);

    boolean existsByArticleInfoIdAndUserId(Long articleId, String userId);
}
