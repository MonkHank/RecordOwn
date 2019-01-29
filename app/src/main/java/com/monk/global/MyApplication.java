package com.monk.global;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-01-03
 */
public class MyApplication extends MultiDexApplication {
    private static final String tag = "MyApplication";
    public static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        long occupyMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        LogUtil.v(tag,"occupyMemory = "+occupyMemory/1024/1024+"\t maxMemory = "+maxMemory/1024/1024);
    }

}
