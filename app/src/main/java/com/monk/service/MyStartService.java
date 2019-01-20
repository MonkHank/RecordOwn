package com.monk.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author monk
 * @date 2018-12-21
 */
public class MyStartService extends Service {
    public MyStartService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
