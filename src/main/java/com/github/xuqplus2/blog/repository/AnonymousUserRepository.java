package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.AnonymousUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousUserRepository extends JpaRepository<AnonymousUser, String> {
}
