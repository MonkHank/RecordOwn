package com.monk.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.support.multidex.MultiDexApplication;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-01-03
 */
public class RecordOwnApplication extends MultiDexApplication {
    public static Application mApplication;
    private final String tag = "MyApplication";
    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = Process.myPid();

        long occupyMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        LogUtil.v(tag,"occupyMemory = "+occupyMemory/1024/1024+"\t maxMemory = "+maxMemory/1024/1024);
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

}
