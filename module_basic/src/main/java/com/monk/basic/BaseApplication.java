package com.monk.basic;

import android.app.Application;
import android.os.Handler;
import android.os.Process;

import androidx.multidex.MultiDexApplication;

import com.monk.commonutils.CrashHandler;
import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-01-03
 */
public class BaseApplication extends MultiDexApplication {
    public static Application mApplication;
    private final String tag = "BaseApplication";
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        handler = new Handler();
        mainThreadId = Process.myPid();

        CrashHandler.getInstance(this).init();

        long occupyMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        LogUtil.v(tag,"占用内存(M) = "+occupyMemory/1024/1024+"\t maxMemory = "+maxMemory/1024/1024);
    }


    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

}