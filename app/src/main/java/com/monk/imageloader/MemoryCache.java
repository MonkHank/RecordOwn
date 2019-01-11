package com.monk.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @author monk
 * @date 2019-01-08
 */
public class MemoryCache implements IImageCache<String,Bitmap> {
    private LruCache<String,Bitmap> lruCache;

    public MemoryCache() {
        final int maxMemory = (int) Runtime.getRuntime().maxMemory()/1024;
        lruCache = new LruCache<String,Bitmap>(maxMemory/8){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // Bitmap所占用的内存空间数等于Bitmap的每一行所占用的空间数乘以Bitmap的行数
                return value.getRowBytes() * value.getHeight() /1024;
            }
        };
    }

    @Override
    public Bitmap put(String key, Bitmap value) {
        return lruCache.put(key,value);
    }

    @Override
    public Bitmap get(String key) {
        return lruCache.get(key);
    }


}
