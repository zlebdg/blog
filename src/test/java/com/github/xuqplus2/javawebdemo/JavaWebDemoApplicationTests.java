package com.github.xuqplus2.javawebdemo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaWebDemoApplicationTests {

  private static final String DEV = "dev";
  private static final String TEST = "test";

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${project.profile}")
  String profile;

  /**
   * 构建环境依据此切换测试用例参数
   * 为什么设计[isProfileTest]这个参数:
   * 开发调试的时候需要频繁修改方法参数, 提交构建/测试的时候就需要注释掉这些参数
   * 为了避免频繁的注释/修改参数/还原代码等操作造成测试用例代码出错. 设想是这样.
   */
  protected boolean isProfileTest;

  @Before
  public void before() {
    isProfileTest = TEST.equals(profile);
    // isProfileTest = DEV.equals(profile);
  }

  @Autowired
  ApplicationContext applicationContext;

  /**
   * 保存测试用例临时数据时, 使用和当前测试类相关的id
   */
  public final String genTestK(String k) {
    if (null == k) {
      throw new RuntimeException("k can't be null");
    }
    return String.format("%s-%s", this.getClass().getName(), k);
  }
}
