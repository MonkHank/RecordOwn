package com.monk.basic

import android.app.Application
import android.os.Handler
import android.os.Process
import androidx.multidex.MultiDexApplication
import com.monk.commonutils.CrashHandler.Companion.getInstance
import com.monk.commonutils.LogUtil

/**
 * @author monk
 * @date 2019-01-03
 */
open class BaseApplication : MultiDexApplication() {
    private val tag = "BaseApplication"
    override fun onCreate() {
        super.onCreate()
        mApplication = this
        handler = Handler()
        mainThreadId = Process.myPid()
        getInstance(this).init()
        val occupyMemory = Runtime.getRuntime().totalMemory()
        val maxMemory = Runtime.getRuntime().maxMemory()
        LogUtil.v(tag, "占用内存(M) = " + occupyMemory / 1024 / 1024 + "\t maxMemory = " + maxMemory / 1024 / 1024)
    }

    companion object {
        var mApplication: Application? = null
        var handler: Handler? = null
            private set
        var mainThreadId = 0
            private set
    }
}