package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.TestData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDataRepository extends JpaRepository<TestData, String> {
    TestData getByK(String k);
}
