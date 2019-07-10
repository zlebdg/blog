package com.github.xuqplus2.javawebdemo.repository;

import com.github.xuqplus2.javawebdemo.domain.GithubUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubUserInfoRepository extends JpaRepository<GithubUserInfo, Long> {
}
