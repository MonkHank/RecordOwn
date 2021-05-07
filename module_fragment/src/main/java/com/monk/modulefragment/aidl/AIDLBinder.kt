package com.monk.modulefragment.aidl

import android.os.RemoteException
import com.monk.modulefragment.aidl.bean.Person

/**
 * AIDL 实现方式
 *
 * IAidlInterface 接口的本地实现类 AIDLBinder
 *
 *
 * 创建生成的本地 Binder 对象，实现 AIDL 制定的方法
 *
 * @author monk
 * @date 2019/1/12
 */
class AIDLBinder(private val mPersons: MutableList<Person>) : IAidlInterface.Stub() {
    @Throws(RemoteException::class)
    override fun addPerson(p: Person) {
        mPersons.add(p)
    }

    @Throws(RemoteException::class)
    override fun getPersonList(): List<Person> {
        return mPersons
    }
}