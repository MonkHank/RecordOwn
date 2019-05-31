package com.monk.aidldemo.binder.manualbinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.monk.aidldemo.bean.Person;
import com.monk.commonutils.LogUtil;

import java.util.List;

/**
 * 定义一个 本地 Binder，实现远程服务接口定制方法
 * 写个远程接口继承自IInterface，写个本地 binder 类实现它
 *
 * @author monk
 * @date 2019-01-15
 */
public class ManualBinder extends Binder implements IPersonInterface {
    private List<Person> mPersons;

    public ManualBinder(List<Person> mPersons) {
        this.attachInterface(this, DESCRIPTOR);
        this.mPersons = mPersons;
    }

    /**
     * ② 手写的 binder 定义个 asInterface(IBinder) 方法
     *
     * @param obj 1. serviceConnection 的 onServiceConnected(IBinder service) 方法中的 service；
     *            2. 也就是 ManualBinder；
     *            3. 还有 Proxy 也是
     * @return
     */
    public static IPersonInterface asInterface(android.os.IBinder obj) {
        LogUtil.v("ManualBinder","ManualBinder obj="+obj);
        if ((obj == null)) {
            return null;
        }
        android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin instanceof IPersonInterface))) {
            return (IPersonInterface) iin;
        }
        return new Proxy(obj);
    }

    @Override
    public void addPerson(Person p) throws RemoteException {
        mPersons.add(p);
    }

    @Override
    public List<Person> getPersonList() throws RemoteException {
        return mPersons;
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    /**
     * ③ 重写onTransact(Parcel,Parcel,int)方法
     * 是运行在另外一个进程中的方法，也就是 Binder 线程中执行的方法
     *
     * @param code
     * @param data
     * @param reply
     * @param flags
     * @return
     * @throws android.os.RemoteException
     */
    @Override
    public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
        LogUtil.i("ManualBinder","ManualBinder code="+code);
        java.lang.String descriptor = DESCRIPTOR;
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(descriptor);
                return true;
            }
            case TRANSACTION_addPerson: {
                data.enforceInterface(descriptor);
                com.monk.aidldemo.bean.Person _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = com.monk.aidldemo.bean.Person.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                this.addPerson(_arg0);
                reply.writeNoException();
                return true;
            }
            case TRANSACTION_getPersonList: {
                data.enforceInterface(descriptor);
                java.util.List<com.monk.aidldemo.bean.Person> _result = this.getPersonList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
            }
            default: {
                return super.onTransact(code, data, reply, flags);
            }
        }
    }

    /**
     * ④ 定义跨进程 Binder 代理类
     * 如果是跨进程那么就调用 远程Binder 在本地的代理Binder
     */
    private static class Proxy implements IPersonInterface {
        private android.os.IBinder mRemote;

        Proxy(android.os.IBinder remote) {
            mRemote = remote;
        }

        @Override
        public android.os.IBinder asBinder() {
            return mRemote;
        }

        public java.lang.String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        /**
         * 除了基本数据类型，其他类型的参数都需要标上方向类型：in(输入), out(输出), inout(输入输出)
         */
        @Override
        public void addPerson(com.monk.aidldemo.bean.Person p) throws android.os.RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                if ((p != null)) {
                    _data.writeInt(1);
                    p.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addPerson, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }

        @Override
        public java.util.List<com.monk.aidldemo.bean.Person> getPersonList() throws android.os.RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            java.util.List<com.monk.aidldemo.bean.Person> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getPersonList, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(com.monk.aidldemo.bean.Person.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }
    }
}
