# Android客户端实现Binder的几种方式

## 第一种方式：AIDL
① AndroidStudio，创建AIDL文件夹，新建包名，com.monk.aidldemo;创建.aidl文件，IAidlInterface.aidl，除去基本类型以外的类型也需要创建.aidl文件，Person.aidl；

② make下项目，在/build/generated/aidl_source_output_dir/debug/compileDebugAidl/out下有对应包名的IAidlInterface类；

③ 创建本地Binder类MyBinder，包名要与.aidl文件一致， 继承 IAidlInterface.Stub 类，实现定义在IAidlInterface.aidl 文件中的方法；

④ 创建MyService 继承 Service ，用bind模式实现内部方法，通过 IBinder onBind(Intent intent) 方法创建MyBinder 对象；

⑤ Activity bindService(intent,serviceConnection,BIND_AUTO_CREATE)，创建 ServiceConnection 对象，从实现方法 onServiceConnected(ComponentName name, IBinder service) 中通过方法 IAidlInterface iAidlInterface = MyBinder.asInterface(service), 拿到远程服务接口 IAidlInterface；
```java
private ServiceConnection mConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        //连接后拿到 Binder，转换成 AIDL，在不同进程会返回不同代理
//        iAidlInterface = ManualBinder.asInterface(service);
        IAidlInterface iAidlInterface = MyBinder.asInterface(service);
        try {
            service.linkToDeath(mDeathRecipient,0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        iAidlInterface = null;
    }
};
```
== AIDL 在不同 APP 间的通信==
- [链接](https://blog.csdn.net/godoge/article/details/79817996)
1. 服务端项目配置
2. 客户端配置，使用隐式 Intent 启动远程服务，指定 action，指定 package；


## 第二种方式：手写Binder
① 定义一个远程服务接口 IPersonInterface 继承 IInterface，添加业务方法和方法符描述
```java
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
```
② 定义本地 Binder 类 ManualBinder，继承 Binder 实现远程服务接口 IPersonInterface；

③ 在本地Binder类 ManualBinder 类定义 IPersonInterface asInterface(IBinder) 方法，实现自定义方法，和来自 Binder 类中的 asBinder()、onTransact(...) 方法，定义静态内部跨进程 Binder 代理类 Proxy；

③ 定义 MyAidlService 继承自 Service，重写 IBinder onBind(Intent intent) 方法，创建 ManualBinder 对象；

④ 同Aidl模式的最后一步

## 第三种方式：Messenger
① 定义 MyMessengerService 继承 Service，创建 Messenger 对象，管理 Handler，从 handleMessage(Message) 方法处理 Messenger，通过 onBind 方法返回 Messenger 的 IBinder对象；
```java
@Override
public void handleMessage(Message msg) {
    if (weakReference.get() != null) {
        LogUtil.v("MyMessengerService", msg.getData().getString("msg"));
        Messenger client = msg.replyTo;
        Message serverMsg = Message.obtain(null, 0);
        Bundle bundle = new Bundle();
        bundle.putString("msgServer", "服务器返回数据");
        serverMsg.setData(bundle);
        try {
            client.send(serverMsg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
```
② Activity 通过 bindService 方式开启 Service，创建 ServiceConnection，在 onServiceConnected(ComponentName,IBinder) 方法中通过 Messenger 发送消息给服务端；
```java
@Override
public void onServiceConnected(ComponentName name, IBinder service) {
    Messenger mMessenger = new Messenger(service);
    Message msg = Message.obtain(null, 0);
    Bundle data = new Bundle();
    data.putString("msg"," send messenger to client");
    msg.setData(data);
    msg.replyTo=mGetReplyMessager;
    try {
        mMessenger.send(msg);
    } catch (RemoteException e) {
        e.printStackTrace();
    }
}
```
③ 创建客户端 Messenger 对象和 Handler对象，接收服务端发送的消息；
```java
 private Messenger mGetReplyMessager = new Messenger(new ClientHandler(this));

private static class ClientHandler extends Handler{
    WeakReference<Activity>weakReference;

    public ClientHandler(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        if (weakReference.get() != null) {
            LogUtil.v("MainActivity",msg.getData().getString("msgServer"));
        }
    }
}

```

==注==
拿到远程服务接口 IAidlInterface 之后，就可以操作其内部定义的方法

手写binder，其实就是复制AIDL生成的远程服务接口实现；
    - [x] Messenger