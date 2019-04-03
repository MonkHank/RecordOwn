package com.monk.aidldemo.binder.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.monk.commonutils.LogUtil;

import java.lang.ref.WeakReference;

/**
 * @author monk
 * @date 2019-01-15
 */
public class MyMessengerService extends Service {

    private Messenger messenger = new Messenger(new MessengerHandler(this));

    private static class MessengerHandler extends Handler {
        WeakReference<Service> weakReference;

        MessengerHandler(Service service) {
            weakReference = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() != null) {
                LogUtil.v("MyMessengerService", msg.getData().getString("msg"));
                Messenger client = msg.replyTo;
                Message serverMsg = Message.obtain(null, 0);
                Bundle bundle = new Bundle();
                bundle.putString("msgServer", "服务器返回数据");
                serverMsg.setData(bundle);
                try {
                    client.send(serverMsg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
