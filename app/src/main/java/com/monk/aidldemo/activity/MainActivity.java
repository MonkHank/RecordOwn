package com.monk.aidldemo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moho.rxjava2.TestRxjava2;
import com.monk.aidldemo.IAidlInterface;
import com.monk.aidldemo.LogUtil;
import com.monk.aidldemo.R;
import com.monk.aidldemo.bean.Person;
import com.monk.aidldemo.service.MyAidlService;

import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author monk
 * @date 2018-12-13
 */
public class MainActivity extends AppCompatActivity {
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
            iAidlInterface = IAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iAidlInterface = null;
        }
    };

    private final String tag = "MainActivity";
    private TextView mTextMessage;
    private IAidlInterface iAidlInterface;
    private AppCompatButton button;
    private LinearLayout myLayout;
    private Disposable subscribe;
    private Disposable subscribe1;
    private Disposable subscribe2;
    private Disposable subscribe3;
    private Disposable subscribe4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage =  findViewById(R.id.message);
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        button = findViewById(R.id.button);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        myLayout=findViewById(R.id.myLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                Person person = new Person("shixin" + random.nextInt(10));
                try {
                    iAidlInterface.addPerson(person);
                    List<Person> personList = iAidlInterface.getPersonList();
                    mTextMessage.setText(personList.toString());
                    ScrollingActivity.intoHere(MainActivity.this);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        Intent intent1 = new Intent(getApplicationContext(), MyAidlService.class);
        bindService(intent1, mConnection, BIND_AUTO_CREATE);

        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.v("事件分发机制",""+v.toString());
            }
        });

        mTextMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtil.i("事件分发机制", event.toString());
                return false;
            }
        });

//        myLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                LogUtil.e("事件分发机制", event.toString());
//                return false;
//            }
//        });

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
