package com.monk;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.monk.aidldemo.R;
import com.monk.aidldemo.activity.ScrollingActivity;
import com.monk.aidldemo.bean.Person;
import com.monk.aidldemo.binder.IPersonInterface;
import com.monk.aidldemo.binder.ManualBinder;
import com.monk.aidldemo.binder.messenger.MyMessengerService;
import com.monk.aidldemo.service.MyAidlService;
import com.monk.eventdispatch.EventDispatchActivity;
import com.monk.rxjava2.TestRxjava2;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author monk
 * @date 2018-12-13
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接后拿到 Binder，转换成 AIDL，在不同进程会返回个代理
            iAidlInterface = ManualBinder.asInterface(service);
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

    private ServiceConnection mMessengerConnection = new ServiceConnection() {
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

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

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

    /**
     * 死亡代理
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (iAidlInterface == null) {
                return;
            }
            iAidlInterface.asBinder().unlinkToDeath(mDeathRecipient, 0);
            iAidlInterface=null;
            Intent intent1 = new Intent(getApplicationContext(), MyAidlService.class);
            bindService(intent1, mConnection, BIND_AUTO_CREATE);
        }
    };

    private final String tag = "MainActivity";
    private TextView mTextMessage;
    private IPersonInterface iAidlInterface;
    private AppCompatButton button,scrollActivityButton;
    private Disposable subscribe;
    private Disposable subscribe1;
    private Disposable subscribe2;
    private Disposable subscribe3;
    private Disposable subscribe4;
    private Button eventDispatchButton;

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                Random random = new Random();
                Person person = new Person("shixin" + random.nextInt(10));
                try {
                    iAidlInterface.addPerson(person);
                    List<Person> personList = iAidlInterface.getPersonList();
                    mTextMessage.setText(personList.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.scrollActivityButton:
                ScrollingActivity.intoHere(MainActivity.this);
                break;
            case R.id.eventDispatchButton:
                startActivity(new Intent(this,EventDispatchActivity.class));
                break;
             default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        mTextMessage =  findViewById(R.id.message);
        button = findViewById(R.id.button);
        scrollActivityButton = findViewById(R.id.scrollActivityButton);
        eventDispatchButton = findViewById(R.id.eventDispatchButton);

        button.setOnClickListener(this);
        scrollActivityButton.setOnClickListener(this);
        eventDispatchButton.setOnClickListener(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent1 = new Intent(getApplicationContext(), MyAidlService.class);
        bindService(intent1, mConnection, BIND_AUTO_CREATE);

        Intent intent2 = new Intent(getApplicationContext(), MyMessengerService.class);
        bindService(intent2, mMessengerConnection, BIND_AUTO_CREATE);



//        TestRxjava2.getInstance().create();

//        subscribe = TestRxjava2.getInstance().mapOperate().subscribe(new Consumer<DouBanMovie>() {
//            @Override
//            public void accept(DouBanMovie douBanMovie) throws Exception {
//                LogUtil.i(tag, "成功:" + douBanMovie.toString());
//                mTextMessage.setText(douBanMovie.title);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                LogUtil.e(tag, "失败：" + throwable.getMessage());
//            }
//        });

//        subscribe1 = TestRxjava2.getInstance().concatOperation().subscribe(new Consumer<DouBanMovie>() {
//            @Override
//            public void accept(DouBanMovie douBanMovie) throws Exception {
//                LogUtil.i(tag,"成功:"+douBanMovie.toString());
//                mTextMessage.setText(douBanMovie.title);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                LogUtil.e(tag,"失败："+throwable.getMessage());
//            }
//        });

//        subscribe2 = TestRxjava2.getInstance().flatMapOperation().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DouBanMovie>() {
//            @Override
//            public void accept(DouBanMovie douBanMovie) throws Exception {
//                LogUtil.i(tag,"成功:"+douBanMovie.toString());
//                mTextMessage.setText(douBanMovie.title);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                LogUtil.e(tag,"失败："+throwable.getMessage());
//            }
//        });

        subscribe3 = TestRxjava2.getInstance().zipOperation().subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                LogUtil.v(tag,"aBoolean："+aBoolean);
            }
        });

        subscribe4 = TestRxjava2.getInstance().intervalOperation().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (aLong == 3) {
                    subscribe4.dispose();
                }
                LogUtil.v(tag,"aLong = "+aLong);

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        unbindService(mMessengerConnection);
        if (subscribe != null) {
            subscribe.dispose();
        }
        if (subscribe1 != null) {
            subscribe1.dispose();
        }
        if (subscribe2 != null) {
            subscribe2.dispose();
        }
        if (subscribe3 != null) {
            subscribe3.dispose();
        }
    }


}
