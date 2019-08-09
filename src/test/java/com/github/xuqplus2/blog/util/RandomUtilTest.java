package com.github.xuqplus2.blog.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomUtilTest {

    private final Logger logger = LoggerFactory.getLogger(RandomUtilTest.class);

    @Test
    public void numiric() {

        String numiric = RandomUtil.numiric(4);

        logger.info(numiric);

        Assert.assertNotNull(numiric);
    }
}
