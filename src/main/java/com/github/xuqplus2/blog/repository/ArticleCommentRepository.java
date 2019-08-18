package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    @Modifying
    @Query("update ArticleComment as a set a.article.id = ?1, a.replyTo.id = ?2 where a.id = ?3")
    void updateArticleIdAndReplyToId(Long articleId, Long replyToId, Long id);

    Page<ArticleComment> findAllByArticleId(Long id, Pageable pageable);

    List<ArticleComment> findAllByReplyTo(ArticleComment articleComment);

    Page<ArticleComment> findAllByArticleIdAndReplyTo(Long id, ArticleComment replyTo, Pageable pageable);

    Page<ArticleComment> findAllByArticleIdAndReplyToNull(Long id, Pageable pageable);
}
