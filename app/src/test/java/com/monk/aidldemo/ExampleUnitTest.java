package com.monk.aidldemo;

import org.junit.Test;

import java.util.Collections;
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
}