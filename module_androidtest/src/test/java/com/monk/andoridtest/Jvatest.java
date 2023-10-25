package com.monk.andoridtest;

import android.util.Log;
import android.util.LruCache;

import org.junit.Test;

import java.util.Map;

public class Jvatest {

    @Test
    public void lruTest() {
        LruCache<Integer, Integer> lruCache = new LruCache<>(5);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);
        lruCache.put(4, 4);
        lruCache.put(5, 5);
        for (Map.Entry<Integer, Integer> entry : lruCache.snapshot().entrySet()) {
            Log.e("LRU", entry.getKey() + ":" + entry.getValue());
        }
        Log.e("LRU", "超出设定存储容量后");
        lruCache.put(6, 6);
        for (Map.Entry<Integer, Integer> entry : lruCache.snapshot().entrySet()) {
            Log.e("LRU", entry.getKey() + ":" + entry.getValue());
        }
    }
}

