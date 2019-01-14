package com.monk;

import android.app.Application;

/**
 * @author monk
 * @date 2019-01-03
 */
public class MyApplication extends Application {
    public static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
