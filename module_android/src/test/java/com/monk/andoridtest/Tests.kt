package com.monk.andoridtest

import android.graphics.Bitmap
import android.util.LruCache
import com.bumptech.glide.Glide
import org.junit.Test

class Tests {
    var lruCache: LruCache<Int, String>? = null

    var imagCache: LruCache<Int, Bitmap>? = null


    @Test
    fun main() {
        lruCache = LruCache(5)
        lruCache?.put(0, "$0")
        // java 打印全为null，去跑一个 android项目就可以正常显示
        println("key:${0} --- value:${lruCache?.get(0)}")
        println("${lruCache!![0]}")
        println(lruCache?.size())

    }

    @Test
    fun test(){
        imagCache = object :LruCache<Int,Bitmap>(3072){
            override fun sizeOf(key: Int?, value: Bitmap?): Int {
                if (value==null)return 0
                return value.width* value.height
            }
        }

        var tempBm50 = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
        imagCache?.put(0, tempBm50)
        var tempBm40 = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
        imagCache?.put(0, tempBm40)


    }

}