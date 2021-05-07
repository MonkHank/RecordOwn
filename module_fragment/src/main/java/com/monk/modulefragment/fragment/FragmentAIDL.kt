package com.monk.modulefragment.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.os.IBinder.DeathRecipient
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.blankj.utilcode.util.ToastUtils
import com.monk.activity.base.BaseFragment
import com.monk.commonutils.LogUtil
import com.monk.modulefragment.R
import com.monk.modulefragment.aidl.AIDLService
import com.monk.modulefragment.aidl.IPersonInterface
import com.monk.modulefragment.aidl.ManualBinder
import com.monk.modulefragment.aidl.MyMessengerService
import com.monk.modulefragment.aidl.bean.Person
import java.lang.Exception
import java.lang.ref.WeakReference
import java.util.*

/**
 * @author monk
 * @date 2019-05-29
 */
class FragmentAIDL : BaseFragment<FragmentAIDL?>() {

    private var aidlButton: AppCompatButton? = null
    private var tvMsg: TextView? = null
    private var tvContent: TextView? = null

    private var iPersonInterface: IPersonInterface? = null

    private val mGetReplyMessager: Messenger = Messenger(ClientHandler(this))

    private class ClientHandler(f: FragmentAIDL?) : Handler() {
        var weakReference: WeakReference<FragmentAIDL?> = WeakReference(f)
        override fun handleMessage(msg: Message) {
            if (weakReference.get() != null) {
                LogUtil.v("MainActivity", msg.data.getString("msgServer"))
                weakReference.get()?.tvMsg?.text=msg.data.getString("msgServer")
            }
        }
    }

    /*** ⑥ 通过bind方法实现本地和服务端Binder关联，拿到远程服务接口 */
    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            LogUtil.v(simpleName, "service:$service")
            //连接后拿到 Binder，转换成 AIDL，在不同进程会返回个代理
            iPersonInterface = ManualBinder.asInterface(service)
            //            IAidlInterface iAidlInterface = AIDLBinder.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient, 0)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            iPersonInterface = null
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fra_modulefra_aidl, container,false)
        aidlButton=view.findViewById(R.id.aidlButton)
        tvMsg=view.findViewById(R.id.tvMsg)
        tvContent=view.findViewById(R.id.fullscreen_content)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = Intent(context, AIDLService::class.java)
        context?.bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

        val intent2 = Intent(context, MyMessengerService::class.java)
        context?.bindService(intent2, mMessengerConnection, Context.BIND_AUTO_CREATE)

        aidlButton?.setOnClickListener {
            val random = Random()
            val person = Person("shixin" + random.nextInt(10))
            try {
                iPersonInterface?.addPerson(person)
                val personList: List<Person> = iPersonInterface?.personList!!
                tvContent?.text=personList.toString()
            } catch (e: RemoteException) {
                e.printStackTrace()
            } catch (e:Exception){
                ToastUtils.showShort(e.message)
            }
        }
    }

    private val mMessengerConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val mMessenger = Messenger(service)
            val msg = Message.obtain(null, 0)
            val data = Bundle()
            data.putString("msg", " send messenger to client")
            msg.data = data
            msg.replyTo = mGetReplyMessager
            try {
                mMessenger.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        /**
         * 类 ServiceConnection 中的 onServiceDisconnected() 方法在正常情况下是不被调用的，
         * 它的调用时机是当Service服务被异外销毁时，例如内存的资源不足时.
         *
         * @param name Service
         */
        override fun onServiceDisconnected(name: ComponentName) {}
    }

    /*** 死亡代理*/
    private val mDeathRecipient: DeathRecipient = object : DeathRecipient {
        override fun binderDied() {
            if (iPersonInterface == null)  return

            iPersonInterface!!.asBinder().unlinkToDeath(this, 0)
            iPersonInterface = null
            val intent1 = Intent(context, AIDLService::class.java)
            context?.bindService(intent1, mConnection, Context.BIND_AUTO_CREATE)
        }
    }



    override fun onDestroyView() {
        context?.unbindService(mConnection)
        context?.unbindService(mMessengerConnection)
        super.onDestroyView()
    }
}