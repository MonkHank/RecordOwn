package com.monk.modulefragment.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.monk.commonutils.LogUtil;
import com.monk.modulefragment.aidl.bean.Person;

import java.util.List;

/**
 * 定义一个 本地 Binder，实现远程服务接口定制方法
 * 写个远程接口继承自IInterface，写个本地 binder 类实现它
 *
 * @author monk
 * @date 2019-01-15
 */
public class ManualBinder extends Binder implements IPersonInterface {
    private final List<Person> mPersons;

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
    public static IPersonInterface asInterface(IBinder obj) {
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
    public void addPerson(Person p)  {
        mPersons.add(p);
    }

    @Override
    public List<Person> getPersonList()  {
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
     * @throws RemoteException
     */
    @Override
    public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws RemoteException {
        LogUtil.i("ManualBinder","ManualBinder code="+code);
        String descriptor = DESCRIPTOR;
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(descriptor);
                return true;
            }
            case TRANSACTION_addPerson: {
                data.enforceInterface(descriptor);
                Person _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = Person.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                this.addPerson(_arg0);
                reply.writeNoException();
                return true;
            }
            case TRANSACTION_getPersonList: {
                data.enforceInterface(descriptor);
                List<Person> _result = this.getPersonList();
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
        private final IBinder mRemote;

        Proxy(IBinder remote) {
            mRemote = remote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        /**
         * 除了基本数据类型，其他类型的参数都需要标上方向类型：in(输入), out(输出), inout(输入输出)
         */
        @Override
        public void addPerson(com.monk.modulefragment.aidl.bean.Person p) throws RemoteException {
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
        public List<com.monk.modulefragment.aidl.bean.Person> getPersonList() {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            List<com.monk.modulefragment.aidl.bean.Person> _result = null;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getPersonList, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(com.monk.modulefragment.aidl.bean.Person.CREATOR);
            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }
    }
}
