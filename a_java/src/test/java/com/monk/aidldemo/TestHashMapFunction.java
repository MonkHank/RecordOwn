package com.monk.aidldemo;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestHashMapFunction {

    /**
     * 大于输入参数且最近的2的整数次幂的数。比如10，则返回16。
     */
    @Test
    public void  tableSizeFor() {
        int cap =3;

        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        int result =  (n < 0) ? 1 : (n >= 100000) ? 100000 : n + 1;
        System.out.println(result);
    }

    @Test
    public void putVal() {
        Map<String, String> map = new HashMap<>(2);
        String put = map.put("one", "hello");
        String put2 = map.put("two", "hello2");
        System.out.println(put);
        System.out.println(put2);
    }

}
