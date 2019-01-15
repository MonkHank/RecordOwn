package com.monk.aidldemo.binder;

import android.os.IInterface;

import com.monk.aidldemo.bean.Person;

import java.util.List;

/**
 * 定义一个远程服务接口
 * @author monk
 * @date 2019-01-15
 */
public interface IPersonInterface extends IInterface {
    String DESCRIPTOR = "com.monk.aidldemo.binder";

    int TRANSACTION_addPerson = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);

    int TRANSACTION_getPersonList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    void addPerson(Person p) throws android.os.RemoteException;

    List<Person> getPersonList() throws android.os.RemoteException;
}
