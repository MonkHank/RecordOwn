package com.monk.imageloader;

/**
 * @author monk
 * @date 2019-01-08
 */
public interface IImageCache<K,V> {
    V put(K key, V value);

    V get(K key);
}
