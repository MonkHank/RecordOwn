package com.monk.commonutils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * @author monk
 * @since 2023/11/18 14:09 周六
 */
object FileUtils {
  const val TAG = "FileUtils"


  /**
   * 加载本地Assets
   * @param context Context
   * @param path String
   * @return String?
   */
  fun loadStringFromLocal(context: Context, path: String): String {
    val inputStream = readInputByPath(context, path)
    var out:FileOutputStream?=null
    var content =""
    inputStream?.let {
      try {
        val bytes = ByteArray(it.available())
        it.read(bytes)
        val file = File(context.filesDir,"test.mp4")
        if(file.exists()){
          return Uri.parse(file.path).path.toString()
        }
        file.createNewFile()
        out = FileOutputStream(file)
        out?.write(bytes)
        out?.flush()
        l.v(TAG, "loadStringFromLocal path:${Uri.parse(file.path).path.toString()}")
        return Uri.parse(file.path).path.toString()
      } catch (e: Exception) {
        e.printStackTrace()
      } finally {
        try {
          it.close()
          out?.close()
        } catch (e: Exception) {
          e.printStackTrace()
        }
      }
    }
    l.v(TAG, "loadStringFromLocal path:$path")
    return content
  }


  /**
   * 根据路径获取InputStream
   * @param context Context
   * @param path String
   * @return InputStream?
   */
  private fun readInputByPath(context: Context, path: String): InputStream? {
    if (path.isBlank()) return null
    var `is`: InputStream? = null
    try {
      `is` = context.assets.open(path)
    } catch (e: IOException) {
      try {
        `is` = FileInputStream(path)
      } catch (_: IOException) {
      }
    }
    return `is`
  }
}