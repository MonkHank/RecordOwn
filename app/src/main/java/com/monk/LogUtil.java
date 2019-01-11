package com.monk;

import android.util.Log;


/**
 * @author Administrator
 * @date 2015-12-27
 */
public class LogUtil {

    /*** 需要开发完毕后，上传市场前，置为false */
    private static boolean isDebug = true;
//	private static boolean isDebug = false;

    private static String Symbol = "-->";

    public static void d(String msg) {
        String lineNumber = "" + Thread.currentThread().getStackTrace()[4].getLineNumber();
        if (isDebug) {
            Log.d("tag", lineNumber + Symbol + msg);
        }
    }

    public static void d(Object object, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isDebug) {
            Log.d(object.getClass().getSimpleName(), autoJumpLogInfos[2] + "-->" + msg);
        }
    }

    public static void d(Object object, String msg, boolean isPrint) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isPrint) {
            Log.d(object.getClass().getSimpleName(), autoJumpLogInfos[2] + "-->" + msg);
        }
    }

    public static void d(String tag, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isDebug) {
            Log.d(tag, autoJumpLogInfos[2] + Symbol + msg);
        }
    }

    public static void e(String msg) {
        String lineNumber = "" + Thread.currentThread().getStackTrace()[4].getLineNumber();
        if (isDebug) {
            Log.e("tag", lineNumber + Symbol + msg);
        }
    }

    public static void e(Object object, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isDebug) {
            Log.e(object.getClass().getSimpleName(), autoJumpLogInfos[2] + Symbol + msg);
        }
    }

    public static void e(String tag, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isDebug) {
            Log.e(tag, autoJumpLogInfos[2] + Symbol + msg);
        }
    }

    public static void e(String tag, String msg, boolean isPrint) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isPrint) {
            Log.e(tag, autoJumpLogInfos[2] + Symbol + msg);
        }
    }

    public static void v(String msg) {
        String lineNumber = "" + Thread.currentThread().getStackTrace()[4].getLineNumber();
        if (isDebug) {
            Log.v("tag", lineNumber + Symbol + msg);
        }
    }

    public static void v(Object object, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isDebug) {
            Log.v(object.getClass().getSimpleName(), autoJumpLogInfos[2] + Symbol + msg);
        }
    }

    public static void v(Object object, String msg, boolean isPrint) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isPrint) {
            Log.v(object.getClass().getSimpleName(), autoJumpLogInfos[2] + Symbol + msg);
        }
    }

    public static void v(String tag, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isDebug) {
            Log.v(tag, autoJumpLogInfos[2] + Symbol + msg);
        }
    }

    public static void i(String msg) {
        String lineNumber = "" + Thread.currentThread().getStackTrace()[4].getLineNumber();
        if (isDebug) {
            Log.i("tag", lineNumber + Symbol + msg);
        }
    }

    public static void i(Object object, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isDebug) {
            Log.i(object.getClass().getSimpleName(), autoJumpLogInfos[2] + Symbol + msg);
        }
    }

    public static void i(String tag, String msg) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isDebug) {
            Log.i(tag, autoJumpLogInfos[2] + Symbol + msg);
        }
    }

    public static void i(String tag, String msg, boolean isPrint) {
        String[] autoJumpLogInfos = getAutoJumpLogInfos();
        if (isPrint) {
            Log.i(tag, autoJumpLogInfos[2] + Symbol + msg);
        }
    }

    private static String[] getAutoJumpLogInfos() {
        // 下标0对应的是类，下标1对应的是所在方法，下标2对应的是所在的类名全路径的行数
        String[] infos = new String[]{"", "", ""};
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        /*
        * elements 的信息包含以下（前5个）
        * dalvik.system.VMStack	-2
        * java.lang.Thread	1566
        * com.moho.peoplesafe.utils.LogUtil	213
        * com.moho.peoplesafe.utils.LogUtil	103
        * com.moho.peoplesafe.base.BaseActivity	117
        * */
        if (elements.length < 5) {
            return infos;
        } else {
            // ClassName
            infos[0] = elements[4].getClassName().substring(elements[4].getClassName().lastIndexOf(".") + 1);
            // MethodName
            infos[1] = "Method：" + elements[4].getMethodName() + "()";
            // LineNumber
            infos[2] = elements[4].getLineNumber() + " 行";
            return infos;
        }
    }
}
