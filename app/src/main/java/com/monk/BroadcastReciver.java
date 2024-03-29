package com.monk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-05-31
 */
public class BroadcastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtil.v("BroadcastReciver","action:"+action);
        if (action == null) {
            return;
        }
        if (action.equals(Intent.ACTION_TIME_TICK)) {
            // 每分钟系统发送一次的广播
        }
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }
}
