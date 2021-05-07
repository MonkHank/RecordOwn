package com.monk.modulefragment.aidl

import android.content.Intent
import android.os.IBinder
import com.monk.activity.base.BaseService
import com.monk.commonutils.LogUtil
import com.monk.modulefragment.aidl.bean.Person
import java.util.*

/**
 * @author monk
 * @date 2018-12-13
 */
class AIDLService : BaseService<AIDLService?>() {
    /**
     * ⑤ 通过 Service 与本地Binder 进行关联
     * 客户端与服务端绑定时的回调，返回 mIBinder 后客户端就可以通过它远程调用服务端的方法，即实现了通讯
     *
     * @param intent
     * @return
     */
    override fun onBind(intent: Intent): IBinder? {
        val mPersons: List<Person> = ArrayList()
        //        return new AIDLBinder(mPersons);
        return ManualBinder(mPersons)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.v(simpleName, "getApplication =$application")
        LogUtil.v(simpleName, "getApplication =$applicationContext")
        LogUtil.v(simpleName, "getBaseContext = $baseContext")
    }
}