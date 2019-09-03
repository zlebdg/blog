package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.UserLiked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikedRepository extends JpaRepository<UserLiked, Long> {

    UserLiked getByArticleInfoIdAndUserId(Long articleId, String userId);

    boolean existsByArticleInfoIdAndUserId(Long articleId, String id1);
}
