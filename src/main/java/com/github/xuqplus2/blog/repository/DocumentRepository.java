package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.Document;
import com.github.xuqplus2.blog.domain.Seal;
import com.github.xuqplus2.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> findAllByUserOrderByCreateAtDesc(User user, Pageable pageable);
}
