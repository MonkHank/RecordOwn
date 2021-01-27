package com.monk.commonutils

import android.content.Context
import java.io.*

/**
 * 最好还是单例，因为还要设置有效时间，静态的话就不能设置时间了；
 *
 * @author Kevin
 * @date 2017/8/15.
 */
object FileCacheUtils {
    private const val tag = "FileCacheUtils"
    private var deadTime = System.currentTimeMillis() + 30 * 60 * 1000
    fun setValidTime(validTime: Long) {
        deadTime = validTime
    }

    /**
     * 30分钟有效期
     */
    @JvmStatic
    fun setCache(context: Context, url: String, json: String?) {
        val cacheDir = context.cacheDir.absolutePath
        LogUtil.v(tag, "设置缓存：$cacheDir\t/$url")
        val cacheFile = File(cacheDir, url)
        var fw: FileWriter? = null
        try {
            fw = FileWriter(cacheFile)
            fw.write("""$deadTime""".trimIndent())
            if (json != null) {
                fw.write(json)
            }
            fw.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            IOUtils.close(fw)
            deadTime = System.currentTimeMillis() + 30 * 60 * 1000
        }
    }

    /**
     * 读取写入缓存时候的缓存时间
     */
    fun getCacheDeadTime(context: Context, url: String?): Long {
        val cacheDir = context.cacheDir
        val cacheFile = File(cacheDir, url)
        if (cacheFile.exists()) {
            var br: BufferedReader? = null
            try {
                br = BufferedReader(FileReader(cacheFile))
                //把时间读出来
                val deadLine = br.readLine()
                return deadLine.toLong()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                IOUtils.close(br)
            }
        }
        return -1
    }

    /**
     * 30分钟有效期
     */
    @JvmStatic
    fun getCache(context: Context, url: String): String {
        val cacheDir = context.cacheDir
        val cacheFile = File(cacheDir, url)
        if (cacheFile.exists()) {
            var br: BufferedReader? = null
            try {
                br = BufferedReader(FileReader(cacheFile))
                //把时间读出来
                val deadLine = br.readLine()
                val deadTime = deadLine.toLong()
                if (System.currentTimeMillis() < deadTime) {
                    val sb = StringBuilder()
                    var line: String?
                    while (br.readLine().also { line = it } != null) {
                        // 去除时间从第二行开始读
                        sb.append(line)
                    }
                    LogUtil.d(tag, "读缓存:\t$url")
                    // 不包括时间
                    return sb.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                IOUtils.close(br)
            }
        }
        return ""
    }

    /**
     * 没有时间期限的缓存
     */
    fun setCacheNoTime(context: Context, url: String, text: String?) {
        //  /data/data/包名/cache目录
        val cacheDir = context.cacheDir.absolutePath
        LogUtil.v(tag, "$cacheDir\n设置缓存$url")
        val cacheFile = File(cacheDir, url)
        var fw: FileWriter? = null
        try {
            //续写
            fw = FileWriter(cacheFile)
            fw.write(text)
            fw.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            IOUtils.close(fw)
        }
    }

    fun getCacheNoTime(context: Context, url: String): String {
        val cacheDir = context.cacheDir
        val cacheFile = File(cacheDir, url)
        if (cacheFile.exists()) {
            var br: BufferedReader? = null
            try {
                br = BufferedReader(FileReader(cacheFile))
                val sb = StringBuilder()
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                LogUtil.v(tag, "读缓存:$url")
                return sb.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                IOUtils.close(br)
            }
        }
        return ""
    }
}