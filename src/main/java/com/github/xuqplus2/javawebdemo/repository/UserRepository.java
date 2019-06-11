package com.github.xuqplus2.javawebdemo.repository;

import com.github.xuqplus2.javawebdemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
