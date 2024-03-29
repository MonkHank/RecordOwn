package com.monk.commonutils

import android.app.Application
import android.os.Looper
import com.blankj.utilcode.util.CloseUtils
import com.blankj.utilcode.util.LogUtils
import java.io.*

/**
 * @author user
 */
class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {
    private val tag = "CrashHandler"
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    private val crash_text = "程序异常，即将退出"

    private object Inner {
        val INSTANCE = CrashHandler()
    }

    fun init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        handleException(ex)
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        mDefaultHandler!!.uncaughtException(thread, ex)
    }

    private fun handleException(ex: Throwable?) {
        if (ex == null) {
            return
        }
        LogUtil.i(tag, Thread.currentThread().name)
        val sError = saveCrashInfo2File(ex)
        ThreadManager.threadPool?.execute {
            Looper.prepare()
            ToastUtils.showToast(mContext, sError)
            writeToLocal(sError)
            Looper.loop()
        }
    }

    private fun writeToLocal(txt: String?) {
        //storage/emulated/0/Android/data/com.millibuf.pkbloodsampling/cache
        val cacheDir = mContext!!.externalCacheDir!!.absolutePath
        LogUtils.v(tag, "设置缓存：$cacheDir")
        val cacheFile = File(cacheDir, "Exception.txt")
        var fw: FileWriter? = null
        try {
            fw = FileWriter(cacheFile)
            fw.write("异常信息：\n".trimIndent())
            if (txt != null) {
                fw.write(txt)
            }
            fw.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            CloseUtils.closeIO(fw)
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String {
        val sb = StringBuilder()
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        try {
            LogUtil.e(sb.toString())
        } catch (e: Exception) {
            LogUtil.i(tag, "an error occured while writing file...")
        }
        return sb.toString()
    }

    companion object {
        private var mContext: Application? = null
        fun getInstance(context: Application?): CrashHandler {
            mContext = context
            return Inner.INSTANCE
        }
    }
}