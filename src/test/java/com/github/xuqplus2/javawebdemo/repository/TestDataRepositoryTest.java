package com.github.xuqplus2.javawebdemo.repository;

import com.github.xuqplus2.javawebdemo.JavaWebDemoApplicationTests;
import com.github.xuqplus2.javawebdemo.domain.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestDataRepositoryTest extends JavaWebDemoApplicationTests {

    @Autowired
    TestDataRepository testDataRepository;

    @Test
    public void test_99_testDataRepository() {
        if (testDataRepository.existsById("k")) {
            TestData testData = testDataRepository.getByK("k");
            logger.info("testData={}", testData);
        } else {
            TestData testData = new TestData().setK("k").setV("v");
            testDataRepository.save(testData);
        }
    }
}