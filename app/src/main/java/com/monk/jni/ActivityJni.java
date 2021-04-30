package com.monk.jni;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.monk.activity.base.BaseCompatActivity;
import com.monk.aidldemo.R;

public class ActivityJni extends BaseCompatActivity<ActivityJni> {


//    static {
//        System.loadLibrary("native-lib");
//    }
//
//    public native String sayHello(String string);

    private FragmentManager fragmentManager;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.fragment_jni);

        fragmentManager = getSupportFragmentManager();

        addAndShowFragment(new JniFragment());
    }

    public void addAndShowFragment(Fragment fragment) {
        // FragmentTransaction表示一次完整的事务，commit() 之前的一系列连贯操作称之为一次事务。
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (mCurrentFragment != null) ft.hide(mCurrentFragment);

        if (!fragment.isAdded()) {
            ft.add(R.id.fragmentContainer, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
        mCurrentFragment = fragment;
    }

}
