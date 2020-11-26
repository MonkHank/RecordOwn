package com.monk.global;

import android.app.Application;
import android.os.Handler;
import android.os.Process;

import androidx.multidex.MultiDexApplication;

import com.monk.basic.BaseApplication;
import com.monk.commonutils.CrashHandler;
import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-01-03
 */
public class RecordOwnApplication extends BaseApplication {
    private final String tag = "RecordOwnApplication";
}
