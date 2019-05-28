package com.monk.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.monk.commonutils.LogUtil;
import com.monk.commonutils.ToastUtils;

/**
 * @author monk
 * @date 2019-05-27
 */
public class BaseCompatActivity<T extends BaseCompatActivity> extends AppCompatActivity {

    protected String tag = BaseCompatActivity.class.getSimpleName();

    protected BaseCompatActivity<T> mContext;
    private String simpleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        simpleName = mContext.getClass().getSimpleName();
        LogUtil.i(tag, simpleName + ":onCreate()");
    }


    public void startActivity(Class<? extends BaseCompatActivity> cls) {
        startActivity(new Intent(mContext, cls));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.destroyExToast();
        ToastUtils.destroyToast();
        LogUtil.e(tag, simpleName + " onDestroy()");
    }

}
