package com.monk.modulefragment.service

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.monk.activity.base.BaseService
import com.monk.commonutils.LogUtil
import java.lang.ref.WeakReference

/**
 * @author monk
 * @date 2018-12-21
 */
class StartService : BaseService<StartService?>() {
    private var demoBinder: DemoBinder? = null
    override fun onBind(intent: Intent): IBinder? {
        LogUtil.i(simpleName, "onBind() -- $intent")
        demoBinder = DemoBinder(mService)
        return demoBinder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        LogUtil.i(tag, "$simpleName：onStartCommand(Intent)")
        return START_STICKY
    }

    class DemoBinder(mService: BaseService<*>?) : Binder(), IBindInterface {
        private val weakReference: WeakReference<BaseService<*>?> = WeakReference(mService)
        override fun execute() {
            LogUtil.i("DemoBinder", "执行了 binder 里面方法 execute()")
        }

        /**
         * 获取 Binder 依赖的 Service，方便调用者使用 Service 里的方法
         *
         * @return
         */
        val service: BaseService<*>?
            get() = if (weakReference.get() != null) {
                weakReference.get()
            } else null

    }

    interface IBindInterface {
        fun execute()
    }
}