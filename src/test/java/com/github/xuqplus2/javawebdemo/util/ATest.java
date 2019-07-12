package com.github.xuqplus2.javawebdemo.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ATest {

    @Test
    public void a() {
        int n = 150;
        List list = new ArrayList();
        for (int i = n; i > 0; i--) {
            for (int j = i; j > 0; ) {
                list.add(j % 10);
                j /= 10;
            }
        }
        Collections.reverse(list);
        System.err.println(list);
    }
}
