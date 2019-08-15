package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    @Modifying
    @Query("update ArticleComment as a set a.article.id = ?1, a.replyTo.id = ?2 where a.id = ?3")
    void updateArticleIdAndReplyToId(Long articleId, Long replyToId, Long id);
}
