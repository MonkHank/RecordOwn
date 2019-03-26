package com.monk.aidldemo;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testMap() {
        Map<String, Object> synchronizedMap = Collections.synchronizedMap(new HashMap<>());

        ConcurrentHashMap concurrentHashMap= new ConcurrentHashMap();

        Hashtable hashtable = new Hashtable();

    }

    @Test
    public void testTimeSeconds() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            String now = df.format(new Date());
            System.out.println("现在时间："+now);
            Date d1 = df.parse(now);
            Date d2 = df.parse("2019-03-26 21:23");
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是毫秒级别
            long days = diff / (1000 * 60 * 60 * 24);

            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            System.out.println("" + days + "天" + hours + "小时" + minutes + "分");

            long secondes = hours * 3600 + minutes * 60;
            System.out.println("秒数：" + secondes);
        } catch (Exception e) {
        }
        Date date = new Date();
        long time = date.getTime();
        long senconds2 = time / (1000 * 60 * 60 * 24);
        System.out.println("January 1, 1970 秒数："+senconds2);
        long roughlyYear = System.currentTimeMillis() / (1000 * 60 * 60 * 24) / 365;
        System.out.println(roughlyYear);
    }
}