package com.monk.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.monk.aidldemo.IAidlInterface;
import com.monk.aidldemo.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @author monk
 * @date 2018-12-13
 */
public class MyAidlService extends Service {
    private List<Person>mPersons;

    public MyAidlService() {
    }

    /*** 创建生成的本地 Binder 对象，实现 AIDL 制定的方法 */
    private IBinder mIBinder = new IAidlInterface.Stub() {
        @Override
        public void addPerson(Person p) throws RemoteException {
            mPersons.add(p);
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return mPersons;
        }
    };

    /**
     * 客户端与服务端绑定时的回调，返回 mIBinder 后客户端就可以通过它远程调用服务端的方法，即实现了通讯
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        mPersons = new ArrayList<>();
        return mIBinder;
    }

}
