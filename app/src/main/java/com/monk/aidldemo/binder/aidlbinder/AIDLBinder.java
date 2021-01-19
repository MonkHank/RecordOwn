package com.monk.aidldemo.binder.aidlbinder;

import android.os.RemoteException;

import com.monk.aidldemo.IAidlInterface;
import com.monk.aidldemo.bean.Person;

import java.util.List;

/**
 * AIDL 实现方式
 *
 * IAidlInterface 接口的本地实现类 AIDLBinder
 *
 * @author monk
 * @date 2019/1/12
 */
public class AIDLBinder extends IAidlInterface.Stub {

    /**
     * 创建生成的本地 Binder 对象，实现 AIDL 制定的方法
     */
    private final List<Person> mPersons;

    public AIDLBinder(List<Person> mPersons) {
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
