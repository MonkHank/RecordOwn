package com.monk.activity.base;

import android.app.Service;
import android.content.Intent;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-5-30 10:59:14
 */
public abstract class BaseService<T extends BaseService<T>> extends Service {
    protected String tag = BaseService.class.getSimpleName();
    protected String simpleName;
    protected BaseService<T> mService;

    @Override
    public void onCreate() {
        super.onCreate();
        mService= this;
        simpleName = mService.getClass().getSimpleName();
        LogUtil.i(tag,simpleName+"：onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(tag,simpleName+"：onStartCommand(Intent)");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.e(tag,simpleName+"：onUnbind(Intent)");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(tag,simpleName+"：onDestroy()");
    }
}