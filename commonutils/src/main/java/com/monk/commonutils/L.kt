package com.monk.commonutils

import android.util.Log

/**
 * @author Administrator
 * @date 2015-12-27
 */
object L {
    private const val Symbol = "--> "

    fun d(msg: String) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.d("tag", getMsg(msg, autoJumpLogInfos, Symbol))
    }

    fun d(`object`: Any, msg: String) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.d(`object`.javaClass.simpleName, getMsg(msg, autoJumpLogInfos, "-->"))
    }

    fun d(`object`: Any, msg: String, isPrint: Boolean) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (isPrint) Log.d(`object`.javaClass.simpleName, getMsg(msg, autoJumpLogInfos, "-->"))
    }

    fun d(tag: String, msg: String) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.d(tag, getMsg(msg, autoJumpLogInfos, Symbol))
    }

    fun e(msg: String) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.e("tag", getMsg(msg, autoJumpLogInfos, Symbol))
    }

    @JvmStatic
    fun w(tag: String?, msg: String?) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.w(tag, getMsg(msg?:"", autoJumpLogInfos, Symbol))
    }

    fun w(msg: String) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.w("tag", getMsg(msg, autoJumpLogInfos, Symbol))
    }

   /* fun e(`object`: Any, msg: String) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.e(`object`.javaClass.simpleName, getMsg(msg, autoJumpLogInfos, Symbol))
    }*/

    @JvmStatic
    fun e(tag: String?, msg: String?) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.e(tag, getMsg(msg?:"", autoJumpLogInfos, Symbol))
    }

    @JvmStatic
    fun e2(tag: String?, msg: String) {
        val infos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.e(tag, infos[2].toString() + Symbol + infos[1] + ":" + msg)
    }

    fun e(tag: String?, msg: String, isPrint: Boolean) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (isPrint) Log.e(tag, getMsg(msg, autoJumpLogInfos, Symbol))
    }

    fun v(`object`: Any?, msg: String?) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.v(`object`?.javaClass?.simpleName, getMsg(msg?:"", autoJumpLogInfos, Symbol))
    }

    @JvmStatic
    fun v(tag: String?, msg: String?) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.v(tag, getMsg(msg?:"", autoJumpLogInfos, Symbol))
    }

    fun v(msg: String) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.v("tag", getMsg(msg, autoJumpLogInfos, Symbol))
    }

    @JvmStatic
    fun v2(tag: String?, msg: String) {
        val infos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.v(tag, infos[2].toString() + Symbol + infos[1] + ":" + msg)
    }

    fun i(msg: String) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.i("tag", getMsg(msg, autoJumpLogInfos, Symbol))
    }

//    fun i(`object`: Any, msg: String) {
//        val autoJumpLogInfos = autoJumpLogInfos
//        if (BuildConfig.DEBUG) Log.i(`object`.javaClass.simpleName, getMsg(msg, autoJumpLogInfos, Symbol))
//    }

    @JvmStatic
    fun i(tag: String?, msg: String?) {
        val infos = autoJumpLogInfos
        if (BuildConfig.DEBUG) Log.i(tag, getMsg(msg?:"", infos, Symbol))

    }

    @JvmStatic
    fun i(tag: String, msg: String, isPrint: Boolean) {
        val autoJumpLogInfos = autoJumpLogInfos
        if (isPrint) Log.i(tag, getMsg(msg, autoJumpLogInfos, Symbol))
    }

    private fun getMsg(msg: String, infos: Array<String?>, symbol: String): String {
        return infos[2].toString() + Symbol + infos[1] + "：" + msg + infos[3]
    }// ClassName

    // MethodName

    // LineNumber

    // Link, 格式严格如下
    // 下标0对应的是类，下标1对应的是所在方法，下标2对应的是所在的类名全路径的行数
    private val autoJumpLogInfos: Array<String?>
        /*
  * elements 的信息包含以下（前5个）
  *
  * dalvik.system.VMStack.getThreadStackTrace(Native Method)
  * java.lang.Thread.getStackTrace(Thread.java:580)
  * com.heybaby.applive.utils.LogUtils.getAutoJumpLogInfos(LogUtils.java:114)
  * com.heybaby.applive.utils.LogUtils.i(LogUtils.java:94)
  * com.heybaby.applive.ui.liveroom.fragments.FragmentZbj.handleSocketMessage(FragmentZbj.java:825)
  */
        get() {
            // 下标0对应的是类，下标1对应的是所在方法，下标2对应的是所在的类名全路径的行数
            val infos = arrayOfNulls<String>(4)
            val elements = Thread.currentThread().stackTrace
            /*
      * elements 的信息包含以下（前5个）
      *
      * dalvik.system.VMStack.getThreadStackTrace(Native Method)
      * java.lang.Thread.getStackTrace(Thread.java:580)
      * com.heybaby.applive.utils.LogUtils.getAutoJumpLogInfos(LogUtils.java:114)
      * com.heybaby.applive.utils.LogUtils.i(LogUtils.java:94)
      * com.heybaby.applive.ui.liveroom.fragments.FragmentZbj.handleSocketMessage(FragmentZbj.java:825)
      */return if (elements.size < 5) {
                infos
            } else {
                // ClassName
                infos[0] = elements[4].className.substring(elements[4].className.lastIndexOf(".") + 1)

                // MethodName
                infos[1] = elements[4].methodName + "()"

                // LineNumber
                infos[2] = elements[4].lineNumber.toString() + " 行"

                // Link, 格式严格如下
                infos[3] = ".(" + elements[4].fileName + ":" + elements[4].lineNumber + ")"
                infos
            }
        }
}