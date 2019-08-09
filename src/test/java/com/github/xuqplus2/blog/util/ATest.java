package com.github.xuqplus2.blog.util;

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

    @Test
    public void b() {
//        System.out.println(new BigDecimal("0.12D")); // exception
//        System.out.println(new BigDecimal("0.12F")); // exception
        // NaN not a number 不是数字
        System.out.println(0F / 0); // NaN
        System.out.println(Double.POSITIVE_INFINITY / Double.POSITIVE_INFINITY); // NaN
        System.out.println(1F / 0); // Infinity
//        System.out.println(1 / 0); // exception
        System.out.println(Double.valueOf("Infinity") / 0); // Infinity
        System.out.println(Double.valueOf("Infinity").equals(Double.valueOf("Infinity") + 3.4E18)); // true
        System.out.println(Double.valueOf("Infinity").equals(Double.valueOf("Infinity"))); // true
        System.out.println(Double.valueOf("Infinity") != Double.valueOf("Infinity")); // true
        System.out.println(Double.valueOf("Infinity") == Double.valueOf("Infinity")); // false
        System.out.println(Double.valueOf("Infinity") < 0); // false
        System.out.println(Double.valueOf("Infinity") > 0); // true
        System.out.println(Double.valueOf("1.12F")); // ok
        System.out.println(Double.valueOf("1.12f")); // ok
        System.out.println(Double.valueOf("1.12D")); // ok
        System.out.println(Double.valueOf("1.12d")); // ok
        System.out.println(Double.valueOf("Infinity")); // ok
        System.out.println(Double.valueOf("-Infinity")); // ok
        System.out.println(Float.valueOf("Infinity")); // ok
        System.out.println(Float.valueOf("-Infinity")); // ok
        System.out.println(Float.MAX_VALUE * 1.9); // 6.465364586132048E38
        System.out.println(Float.MAX_VALUE * 1.5); // 5.104235199577933E38
        System.out.println(Float.MAX_VALUE * 2); // Infinity
        System.out.println(Float.MAX_VALUE + 1); // 3.4028235E38
        System.out.println(Float.MAX_VALUE); // 3.4028235E38
        System.out.println(Integer.MAX_VALUE + 2); // -2147483647
        System.out.println(Integer.MAX_VALUE + 1); // -2147483648
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Float.valueOf("0.12D")); // ok
        System.out.println(Float.valueOf("0.12d")); // ok
        System.out.println(Float.valueOf("0.12f")); // ok
        System.out.println(Float.valueOf("0.12F")); // ok
        System.out.println(Float.valueOf("0.12"));
        System.out.println(Float.valueOf("0"));
        System.out.println(Long.valueOf("0"));

//        System.out.println(Long.valueOf("Infinity")); // exception
//        System.out.println(Long.valueOf("0.12")); // exception
    }
}
