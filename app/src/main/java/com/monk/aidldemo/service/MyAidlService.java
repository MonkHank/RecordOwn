package com.monk.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.monk.aidldemo.bean.Person;
import com.monk.aidldemo.binder.ManualBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author monk
 * @date 2018-12-13
 */
public class MyAidlService extends Service {

    public MyAidlService() {
    }

    /**
     * 客户端与服务端绑定时的回调，返回 mIBinder 后客户端就可以通过它远程调用服务端的方法，即实现了通讯
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        List<Person> mPersons = new ArrayList<>();
//        return new MyBinder(mPersons);
        return new ManualBinder(mPersons);
    }

}
