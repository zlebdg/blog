package com.github.xuqplus2.javawebdemo.repository;

import com.github.xuqplus2.javawebdemo.domain.AlipayUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlipayUserInfoRepository extends JpaRepository<AlipayUserInfo, String> {
}
