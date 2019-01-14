package com.monk;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.monk.rxjava2.TestRxjava2;
import com.monk.aidldemo.IAidlInterface;
import com.monk.aidldemo.R;
import com.monk.aidldemo.activity.ScrollingActivity;
import com.monk.aidldemo.bean.Person;
import com.monk.eventdispatch.EventDispatchActivity;
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
                    ScrollingActivity.intoHere(MainActivity.this);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
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
        eventDispatchButton = findViewById(R.id.eventDispatchButton);

        button.setOnClickListener(this);
        eventDispatchButton.setOnClickListener(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent1 = new Intent(getApplicationContext(), MyAidlService.class);
        bindService(intent1, mConnection, BIND_AUTO_CREATE);


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
