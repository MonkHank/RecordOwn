package com.monk.aidldemo.threecache;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.Map;

/**
 * @author monk
 * @date 2018-12-27
 */
public class ImageCache extends LruCache<String,Bitmap> {
    private Map<String,SoftReference<Bitmap>>cacheMap;
    public ImageCache(Map<String,SoftReference<Bitmap>>cacheMap) {
        super((int) (Runtime.getRuntime().maxMemory()/8));
        this.cacheMap=cacheMap;
    }

    /**
     * 获取图片大小
     * @param key
     * @param value
     * @return
     */
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    /**
     * 当有图片从LruCache中移除时，将其放进软引用集合中
     * @param evicted
     * @param key
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        if (oldValue != null) {
            SoftReference<Bitmap> softReference = new SoftReference<>(oldValue);
            cacheMap.put(key, softReference);
        }
    }

    public Map<String, SoftReference<Bitmap>> getCacheMap() {
        return cacheMap;
    }
}
