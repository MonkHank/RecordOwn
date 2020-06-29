package com.monk.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BaseActivity> extends AppCompatActivity {
    protected T mContext;
    protected String simpleName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this, getClass());

        setContentView(initLayoutResId());

        ImmersionBar.with(this)
                .statusBarDarkFont(true,0.2f)
                .keyboardEnable(true)
                .init();

        ButterKnife.bind(this);

        mContext = (T) this;
        simpleName = mContext.getClass().getSimpleName();

        LogUtils.i("BaseActivity", mContext.getClass().getSimpleName() + " -------------------- onCreate()");


        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        LogUtils.e("BaseActivity", mContext.getClass().getSimpleName() + " -------------------- onDestroy()");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }


    protected void startActivity(Class<? extends BaseActivity> cls) {
        startActivity(new Intent(mContext, cls));
    }

    protected void initToolbar() {
        Toolbar toolBar = findViewById(R.id.toolbar);
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(simpleName);
            }
        }
    }

    /** 点击actionBar返回按钮*/
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    protected abstract int initLayoutResId();
    protected abstract void initData();
}
