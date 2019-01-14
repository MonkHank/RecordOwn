package com.monk.aidldemo.service;

import android.os.RemoteException;

import com.monk.aidldemo.IAidlInterface;
import com.monk.aidldemo.bean.Person;

import java.util.List;

/**
 * @author monk
 * @date 2019/1/12
 */
public class MyBinder extends IAidlInterface.Stub {

    /*** 创建生成的本地 Binder 对象，实现 AIDL 制定的方法 */

    private List<Person> mPersons;

    public MyBinder(List<Person> mPersons) {
        this.mPersons = mPersons;
    }

    @Override
    public void addPerson(Person p) throws RemoteException {
        mPersons.add(p);
    }

    @Override
    public List<Person> getPersonList() throws RemoteException {
        return mPersons;
    }
}
