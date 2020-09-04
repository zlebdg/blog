package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.Seal;
import com.github.xuqplus2.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SealRepository extends JpaRepository<Seal, Long> {

    Page<Seal> findAllByUserOrderByCreateAtDesc(User user, Pageable pageable);
}
