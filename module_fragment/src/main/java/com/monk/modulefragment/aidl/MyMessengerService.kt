package com.monk.modulefragment.aidl

import android.app.Service
import android.content.Intent
import android.os.*
import com.monk.commonutils.LogUtil
import java.lang.ref.WeakReference

/**
 * @author monk
 * @date 2019-01-15
 */
class MyMessengerService : Service() {
    private val messenger = Messenger(MessengerHandler(this))

    private class MessengerHandler internal constructor(service: Service?) : Handler() {
        var weakReference: WeakReference<Service?> = WeakReference(service)
        override fun handleMessage(msg: Message) {
            if (weakReference.get() != null) {
                LogUtil.v("MyMessengerService", msg.data.getString("msg"))
                val client = msg.replyTo
                val serverMsg = Message.obtain(null, 0)
                val bundle = Bundle()
                bundle.putString("msgServer", "服务器返回数据")
                serverMsg.data = bundle
                try {
                    client.send(serverMsg)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }

    }

    override fun onBind(intent: Intent): IBinder? {
        return messenger.binder
    }
}