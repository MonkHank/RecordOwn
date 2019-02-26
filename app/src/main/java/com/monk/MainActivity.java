package com.monk;

import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.monk.activity.AidlFullscreenActivity;
import com.monk.activity.LoginActivity;
import com.monk.activity.ScrollingActivity;
import com.monk.aidldemo.R;
import com.monk.base.OnFragmentInteractionListener;
import com.monk.commonutils.LogUtil;
import com.monk.commonutils.ToastUtils;
import com.monk.customview.fragment.CustomViewFragment;
import com.monk.customview.fragment.CustomViewFragment2;
import com.monk.eventdispatch.EventDispatchActivity;
import com.monk.jni.JniFragment;
import com.monk.location.LocationFragment;
import com.monk.rxjava2.RxJava2Fragment;

/**
 * @author monk
 * @date 2018-12-13
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        OnFragmentInteractionListener {

    private JniFragment jniFragment;
    private RxJava2Fragment rxJava2Fragment;
    private CustomViewFragment customViewFragment1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // FragmentTransaction表示一次完整的事务
            FragmentTransaction ft = fragmentManager.beginTransaction();
            hideFragment(ft);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (rxJava2Fragment == null) {
                        rxJava2Fragment = RxJava2Fragment.newInstance("RxJava2", "");
                        ft.add(R.id.fragmentContainer, rxJava2Fragment).commit();
                    } else {
                        ft.show(rxJava2Fragment).commit();
                    }
                    return true;
                case R.id.navigation_jni:
                    if (jniFragment == null) {
                        jniFragment = new JniFragment();
                        ft.add(R.id.fragmentContainer, jniFragment).commit();
                    } else {
                        ft.show(jniFragment).commit();
                    }
                    return true;
                case R.id.navigation_custom_view:
                    if (customViewFragment1 == null) {
                        customViewFragment1 = CustomViewFragment.newInstance("", "");
                        ft.add(R.id.fragmentContainer, customViewFragment1).commit();
                    } else {
                        ft.show(customViewFragment1).commit();
                    }
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    private final String tag = "MainActivity";
    private TextView mTextMessage;

    private AppCompatButton button, eventDispatchButton, scrollActivityButton, loginButton;
    private UriMatcher uriMatcher;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        mTextMessage = findViewById(R.id.message);
        button = findViewById(R.id.button);
        scrollActivityButton = findViewById(R.id.scrollActivityButton);
        eventDispatchButton = findViewById(R.id.eventDispatchButton);
        loginButton = findViewById(R.id.loginButton);

        button.setOnClickListener(this);
        scrollActivityButton.setOnClickListener(this);
        eventDispatchButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
//        findViewById(R.id.navigation_custom_view).performClick();


        new Thread(() -> {
            Looper.prepare();
//                Toast.makeText(MainActivity.this,"提示",Toast.LENGTH_LONG).show();
            loginButton.setText("登录界面");
            Looper.loop();
            LogUtil.i(tag, "之心");
        }).start();
        initUri();
    }

    private void hideFragment(FragmentTransaction ft) {
        if (jniFragment != null) {
            ft.hide(jniFragment);
        }
        if (rxJava2Fragment != null) {
            ft.hide(rxJava2Fragment);
        }
        if (customViewFragment1 != null) {
            ft.hide(customViewFragment1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(this, AidlFullscreenActivity.class));
                break;
            case R.id.scrollActivityButton:
                ScrollingActivity.intoHere(MainActivity.this);
                break;
            case R.id.eventDispatchButton:
                startActivity(new Intent(this, EventDispatchActivity.class));
                break;
            case R.id.loginButton:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
                break;
        }
    }

    private final int customViewFragment = 0;
    private final int customViewFragment2 = 1;
    private final int locationFragment = 3;

    private void initUri() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(getPackageName(), CustomViewFragment.class.getName(), customViewFragment);
        uriMatcher.addURI(getPackageName(), CustomViewFragment2.class.getName(), customViewFragment2);
        uriMatcher.addURI(getPackageName(), RxJava2Fragment.class.getName(), locationFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        int match = uriMatcher.match(uri);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LogUtil.v(tag, uri.toString() + "- - - " + match);
        switch (match) {
            case customViewFragment:
                ft.replace(R.id.fragmentContainer, CustomViewFragment2.newInstance());
                ft.addToBackStack(CustomViewFragment.class.getSimpleName());
                ft.commit();
                break;
            case customViewFragment2:
                ft.replace(R.id.fragmentContainer, DragViewFragment.newInstance());
                ft.addToBackStack(CustomViewFragment2.class.getSimpleName());
                ft.commit();
                break;
            case locationFragment:
                ft.replace(R.id.fragmentContainer, LocationFragment.newInstance());
                ft.addToBackStack(RxJava2Fragment.class.getSimpleName());
                ft.commit();
                break;
            default:
                break;
        }
    }


    private long currentTime;

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - currentTime < 2000) {
            finish();
        } else {
            ToastUtils.showImageToast(this, "再按一次退出");
        }
        currentTime = time;
    }
}
