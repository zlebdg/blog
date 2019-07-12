package com.github.xuqplus2.javawebdemo.repository;

import com.github.xuqplus2.javawebdemo.domain.TestData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDataRepository extends JpaRepository<TestData, String> {
    TestData getByK(String k);
}
