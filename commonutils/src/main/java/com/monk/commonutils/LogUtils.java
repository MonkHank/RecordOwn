package com.monk.commonutils;

import android.support.annotation.NonNull;
import android.util.Log;


/**
 * @author Administrator
 * @date 2015-12-27
 */
public class LogUtils {
    private static String Symbol = "--> ";

    public static void d(String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG) Log.d("tag", getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void d(Object object, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG) Log.d(object.getClass().getSimpleName(), getMsg(msg, autoJumpLogInfos, "-->"));
    }

    public static void d(Object object, String msg, boolean isPrint) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isPrint) Log.d(object.getClass().getSimpleName(), getMsg(msg, autoJumpLogInfos, "-->"));
    }

    public static void d(String tag, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG)  Log.d(tag, getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void e(String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG) Log.e("tag", getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void w(String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG)  Log.w("tag", getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void e(Object object, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG) Log.e(object.getClass().getSimpleName(), getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void e(String tag, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG)  Log.e(tag, getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void e2(String tag, String msg) {
        String[] infos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG) Log.e(tag, infos[2] + Symbol +  infos[1]+":" +msg);
    }

    public static void e(String tag, String msg, boolean isPrint) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isPrint) Log.e(tag, getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void v(Object object, String msg, boolean isPrint) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isPrint) Log.v(object.getClass().getSimpleName(), getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void v(String tag, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG) Log.v(tag, getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void v(String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG) Log.v("tag", getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void v2(String tag, String msg) {
        String[] infos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG) Log.v(tag, infos[2] + Symbol +  infos[1]+":" +msg);
    }

    public static void i(String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG) Log.i("tag", getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void i(Object object, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG)Log.i(object.getClass().getSimpleName(), getMsg(msg, autoJumpLogInfos, Symbol));
    }

    public static void i(String tag, String msg) {
        String[] infos = getAutoJumpLogInfos();
        if (BuildConfig.DEBUG) Log.i(tag, getMsg(msg, infos, Symbol));
    }


    public static void i(String tag, String msg, boolean isPrint) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isPrint) Log.i(tag, getMsg(msg, autoJumpLogInfos, Symbol));
    }

    @NonNull
    private static String getMsg(String msg, String[] infos, String symbol) {
        return infos[2] + Symbol +  infos[1] + "："  + msg +infos[3];
    }


    private static String[] getAutoJumpLogInfos() {
        // 下标0对应的是类，下标1对应的是所在方法，下标2对应的是所在的类名全路径的行数
        String[] infos = new String[4];

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        /*
         * elements 的信息包含以下（前5个）
         *
         * dalvik.system.VMStack.getThreadStackTrace(Native Method)
         * java.lang.Thread.getStackTrace(Thread.java:580)
         * com.heybaby.applive.utils.LogUtils.getAutoJumpLogInfos(LogUtils.java:114)
         * com.heybaby.applive.utils.LogUtils.i(LogUtils.java:94)
         * com.heybaby.applive.ui.liveroom.fragments.FragmentZbj.handleSocketMessage(FragmentZbj.java:825)
         */
        if (elements.length < 5) {
            return infos;
        } else {
            // ClassName
            infos[0] = elements[4].getClassName().substring(elements[4].getClassName().lastIndexOf(".") + 1);

            // MethodName
            infos[1] = elements[4].getMethodName() + "()";

            // LineNumber
            infos[2] = elements[4].getLineNumber() + " 行";

            // Link, 格式严格如下
            infos[3] = ".(" + elements[4].getFileName() + ":" + elements[4].getLineNumber() + ")";
            return infos;
        }
    }

}
