package com.monk;

import android.annotation.SuppressLint;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.monk.activity.base.BaseCompatActivity;
import com.monk.activity.base.BaseFragment;
import com.monk.activity.base.OnFragmentInteractionListener;
import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;
import com.monk.commonutils.ThreadPoolManager;
import com.monk.customview.fragment.CustomViewFragment;
import com.monk.customview.fragment.CustomViewFragment2;
import com.monk.fragments.SampleFragment;
import com.monk.jni.JniFragment;
import com.monk.location.LocationFragment;
import com.monk.rxjava2.RxJava2Fragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author monk
 * @date 2018-12-13
 */
public class MainActivity extends BaseCompatActivity<MainActivity> implements
        OnFragmentInteractionListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.message) TextView tvMessage;
    @BindView(R.id.navigation) BottomNavigationView navigation;

    private FragmentManager fragmentManager;
    private BaseFragment sampleFragment, jniFragment,rxJava2Fragment, customViewFragment1;
    private BaseFragment mCurrentFragment;

    private UriMatcher uriMatcher;
    private final int customViewFragment = 0;
    private final int customViewFragment2 = 1;
    private final int locationFragment = 3;
    private int menuItemItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.activity_main);
        navigation.setOnNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            sampleFragment = (BaseFragment) fragmentManager.findFragmentByTag(SampleFragment.class.getName());
            jniFragment = (BaseFragment) fragmentManager.findFragmentByTag(JniFragment.class.getName());
            rxJava2Fragment = (BaseFragment) fragmentManager.findFragmentByTag(RxJava2Fragment.class.getName());
            customViewFragment1 = (BaseFragment) fragmentManager.findFragmentByTag(CustomViewFragment.class.getName());
            menuItemItemId=savedInstanceState.getInt("menuItemItemId");
            navigation.setSelectedItemId(menuItemItemId);
        }else {
            // TODO: 2019-05-29 如果menuItemItemId 和其它resId一样，这样就不行了
            navigation.setSelectedItemId(R.id.navigation_sample);
        }

        ThreadPoolManager.getInstance().execute(() -> {
            Looper.prepare();
//            ToastUtils.showImageToast(mContext, "奔溃");
            tvMessage.setText("子线程更新UI问题");
            Looper.loop();
            LogUtil.i(simpleName, "之心");
        });
        initUri();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItemItemId = menuItem.getItemId();
        switch (menuItem.getItemId()) {
            case R.id.navigation_sample:
                if (sampleFragment == null) {
                    sampleFragment = new SampleFragment();
                }
                addAndShowFragment(sampleFragment);
                return true;
            case R.id.navigation_home:
                if (rxJava2Fragment == null) {
                    rxJava2Fragment = RxJava2Fragment.newInstance("RxJava2", "");
                }
                addAndShowFragment(rxJava2Fragment);
                return true;
            case R.id.navigation_jni:
                if (jniFragment == null) {
                    jniFragment = new JniFragment();
                }
                addAndShowFragment(jniFragment);
                return true;
            case R.id.navigation_custom_view:
                if (customViewFragment1 == null) {
                    customViewFragment1 = CustomViewFragment.newInstance("", "");
                }
                addAndShowFragment(customViewFragment1);
                return true;
            default:
                break;
        }
        return false;
    }

    private void addAndShowFragment(BaseFragment fragment) {
        // FragmentTransaction表示一次完整的事务，commit() 之前的一系列连贯操作称之为一次事务。
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (mCurrentFragment != null) {
            ft.hide(mCurrentFragment);
        }
        if (!fragment.isAdded()) {
            ft.add(R.id.fragmentContainer, fragment,fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
        mCurrentFragment=fragment;
    }

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
        LogUtil.v(simpleName, uri.toString() + "- - - " + match);
        switch (match) {
            case customViewFragment:
                ft.replace(R.id.fragmentContainer, CustomViewFragment2.newInstance())
                        .addToBackStack(CustomViewFragment.class.getSimpleName())
                        .commit();
                break;
            case customViewFragment2:
                ft.replace(R.id.fragmentContainer, DragViewFragment.newInstance())
                        .addToBackStack(CustomViewFragment2.class.getSimpleName())
                        .commit();
                break;
            case locationFragment:
                ft.replace(R.id.fragmentContainer, LocationFragment.newInstance())
                        .addToBackStack(RxJava2Fragment.class.getSimpleName())
                        .commit();
                break;
            default:
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.btTestAttach,R.id.btAdd})
    public void clickEvent(View v) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(v.getId()){
            case R.id.btTestAttach:
                if (sampleFragment.isAdded()) {
                    ft.detach(sampleFragment).commit();
                }else {
                    ft.attach(sampleFragment).commit();
                }
                LogUtil.i(tag,"sampleFragment.isAdded()："+sampleFragment.isAdded());
                LogUtil.i(tag,"sampleFragment.isDetach()："+sampleFragment.isDetached());
                break;
            case R.id.btAdd:
                ft.replace(R.id.fragmentContainer, sampleFragment).commit();
                break;
             default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("menuItemItemId", menuItemItemId);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment t : fragments){
            LogUtil.w(tag,"fragment:"+t.getClass().getSimpleName());
        }
    }
}
