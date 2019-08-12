package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
