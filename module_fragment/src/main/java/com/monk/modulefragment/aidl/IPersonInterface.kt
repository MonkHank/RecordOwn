package com.monk.modulefragment.aidl

import android.os.IBinder
import android.os.IInterface
import android.os.RemoteException
import com.monk.modulefragment.aidl.bean.Person

/**
 * ① 定义一个远程服务接口，继承自 IInterface
 *
 * @author monk
 * @date 2019-01-15
 */
interface IPersonInterface : IInterface {

    companion object {
        const val DESCRIPTOR  = "com.monk.modulefragment.aidl.IAidlInterface"
        const val TRANSACTION_addPerson = IBinder.FIRST_CALL_TRANSACTION + 0
        const val TRANSACTION_getPersonList = IBinder.FIRST_CALL_TRANSACTION + 1
    }

    @Throws(RemoteException::class)
    fun addPerson(p: Person?)

//    @Throws(RemoteException::class)
//    fun getPersonList(): List<Person?>

    @get:Throws(RemoteException::class)
    val personList:List<Person>?


}