package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.UserStarred;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStarredRepository extends JpaRepository<UserStarred, Long> {

    UserStarred getByArticleInfoIdAndUserId(Long articleId, String userId);

    boolean existsByArticleInfoIdAndUserId(Long articleId, String userId);
}
