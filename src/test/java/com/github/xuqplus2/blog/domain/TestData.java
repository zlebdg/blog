package com.github.xuqplus2.blog.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestData {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestData.class);

    @Id
    private String k;
    private String v;

    public TestData() {
    }

    public TestData(String k, String v) {
        if (null == k) {
            throw new RuntimeException("k can't be null");
        }
        if (!k.contains(".")) {
            LOGGER.warn("建议使用与当前测试类相关的id, 从而避免测试用例数据错误.");
        }
        this.k = k;
        this.v = v;
    }

    public String getK() {
        return k;
    }

    public TestData setK(String k) {
        this.k = k;
        return this;
    }

    public String getV() {
        return v;
    }

    public TestData setV(String v) {
        this.v = v;
        return this;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "v='" + v + '\'' +
                ", k='" + k + '\'' +
                '}';
    }
}
