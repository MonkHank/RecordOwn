package com.monk.kotlinstudy

import android.util.LruCache
import com.monk.commonutils.l

/**
 * @author monk
 * @since 2023/10/26 10:34
 */
class LruCache {

  fun test() {
    val lruCache = LruCache<Int,String>(5)
    lruCache.put(0, "000")
    l.i("ccccccccccccc","  value:${lruCache[0]}")
    println("key:${0} --- value:${lruCache?.get(0)}")
    println("${lruCache[0]}")
    println(lruCache?.size())
  }
}