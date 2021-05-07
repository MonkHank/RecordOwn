package com.monk.modulefragment.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.monk.activity.base.BaseFragment
import com.monk.commonutils.LogUtil
import com.monk.commonutils.ToastUtils
import com.monk.modulefragment.R
import com.monk.modulefragment.service.StartService

/**
 * @author monk
 * @date 2019-05-29
 */
class FragmentService : BaseFragment<FragmentService?>() ,View.OnClickListener{

    private val TAG="FragmentService"

    private var myStartService:Intent?=null
    private var isServiceBind = false

    private var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            LogUtil.v(simpleName, "componentName:$name\tIBinder:$service")
            val demoBinder: StartService.DemoBinder = service as StartService.DemoBinder
            demoBinder.execute()
        }

        /**
         * 类 ServiceConnection 中的 onServiceDisconnected() 方法在正常情况下是不被调用的，
         * 它的调用时机是当Service服务被异外销毁时，例如内存的资源不足时.
         *
         * @param name Service
         */
        override fun onServiceDisconnected(name: ComponentName) {
            LogUtil.e(simpleName, "componentName:$name")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fra_modulefra_service, container, false)

        view.findViewById<Button>(R.id.btStartService).setOnClickListener(this)
        view.findViewById<Button>(R.id.btStopService).setOnClickListener(this)
        view.findViewById<Button>(R.id.btBindService).setOnClickListener(this)
        view.findViewById<Button>(R.id.btUnbindService).setOnClickListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myStartService = Intent(context, StartService::class.java)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btStartService -> context?.startService(myStartService)
            R.id.btStopService -> {
                val isStopService = context?.stopService(myStartService)
                LogUtil.v(TAG, "isStopService:$isStopService")
            }
            R.id.btBindService -> isServiceBind = context?.bindService(myStartService, serviceConnection, Context.BIND_AUTO_CREATE)!!
            R.id.btUnbindService -> {
                if (isServiceBind) {
                    context?.unbindService(serviceConnection)
                } else {
                    val fm: FragmentManager = fragmentManager!!
                    val ft = fm.beginTransaction()
                    ft.add(DFDemo.newInstance(), "DialogFragment")
                    ft.commit()

                    ToastUtils.showToast(context, "已经解绑过了")
                }
            }
        }
    }
}