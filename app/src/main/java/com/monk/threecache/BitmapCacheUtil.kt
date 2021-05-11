package com.monk.threecache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.ImageView
import com.monk.commonutils.ThreadPoolManager.instance
import java.io.*
import java.lang.ref.SoftReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * @author monk
 * @date 2018-12-27
 */
class BitmapCacheUtil private constructor(private val context: Context) {
    private var imageCache: ImageCache? = null
    fun getInstance(context: Context): BitmapCacheUtil? {
        synchronized(this) {
            if (httpUtil == null) {
                synchronized(this) { httpUtil = BitmapCacheUtil(context) }
            }
        }
        return httpUtil
    }

    private fun getBytesFromWeb(path: String): ByteArray? {
        val url: URL
        var baos: ByteArrayOutputStream? = null
        var `in`: InputStream? = null
        var b: ByteArray? = null
        try {
            url = URL(path)
            val conn = url.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.connectTimeout = 10000
            conn.readTimeout = 10000
            conn.connect()
            if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                `in` = conn.inputStream
                baos = ByteArrayOutputStream()
                val temp = ByteArray(1024)
                var length: Int
                while (`in`.read(temp).also { length = it } != -1) {
                    baos.write(temp, 0, length)
                }
                b = baos.toByteArray()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (baos != null) {
                try {
                    baos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return b
    }

    private fun writeFileToStorage(fileName: String, b: ByteArray) {
        var fos: FileOutputStream? = null
        val file = File(context.filesDir, fileName)
        try {
            fos = FileOutputStream(file)
            fos.write(b)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun readBytesFromStorage(fileName: String): ByteArray? {
        var b: ByteArray? = null
        var fis: FileInputStream? = null
        var baos: ByteArrayOutputStream? = null
        try {
            fis = context.openFileInput(fileName)
            baos = ByteArrayOutputStream()
            val temp = ByteArray(1024)
            var len: Int
            while (fis.read(temp).also { len = it } != -1) {
                baos.write(temp, 0, len)
            }
            b = baos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (baos != null) {
                try {
                    baos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return b
    }

    /**
     * 将图片添加到缓存中
     * @param fileName
     * @param data
     */
    private fun putBitmapIntoCache(fileName: String, data: ByteArray) {
        writeFileToStorage(fileName, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            // 将图片存入强引用
            imageCache!!.put(fileName, BitmapFactory.decodeByteArray(data, 0, data.size))
        }
    }

    /**
     * 从缓存中取出图片
     * @param fileName
     * @return
     */
    private fun getBitmapFromCache(fileName: String): Bitmap? {
        // 从强引用中取出图片
        var bm: Bitmap? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            bm = imageCache!![fileName]
            if (bm == null) {
                val cacheMap = imageCache!!.cacheMap
                val softReference = cacheMap[fileName]
                if (softReference != null) {
                    bm = softReference.get()
                    imageCache!!.put(fileName, bm)
                } else {
                    val data = readBytesFromStorage(fileName)
                    if (data != null && data.size > 0) {
                        bm = BitmapFactory.decodeByteArray(data, 0, data.size)
                        imageCache!!.put(fileName, bm)
                    }
                }
            }
        }
        return bm
    }

    /**
     * 使用三级缓存为ImageView设置图片(LruCache,SoftReference,FileDir)
     * @param path
     * @param view
     */
    fun setImageToView(path: String, view: ImageView) {
        val fileName = path.substring(path.lastIndexOf(File.separator) + 1)
        val bm = getBitmapFromCache(fileName)
        if (bm != null) {
            view.setImageBitmap(bm)
        } else {
            instance!!.execute {
                val b = getBytesFromWeb(path)
                if (b != null && b.size > 0) {
                    // 将图片字节数组写入到缓存中
                    putBitmapIntoCache(fileName, b)
                    val bm = BitmapFactory.decodeByteArray(b, 0, b.size)
                    view.post { view.setImageBitmap(bm) }
                }
            }
        }
    }

    companion object {
        @Volatile
        private var httpUtil: BitmapCacheUtil? = null
    }

    init {
        val cacheMap: Map<String, SoftReference<Bitmap>> = HashMap()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            imageCache = ImageCache(cacheMap)
        }
    }
}