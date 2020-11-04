package com.peoplesafe.adapter;

import android.content.Context;
import android.view.View;

import com.monk.activity.base.BaseCompatActivity;
import com.monk.commonutils.ToastUtils;

import butterknife.ButterKnife;

/**
 * @author JackieHank
 * @date 2017-08-22 17:41.
 */

public abstract class BasePage<T extends BaseCompatActivity> {
    public View mRootView;

    public T mContext;

    /**
     * 在构造方法中可以对控件进行显示和隐藏
     */
    public BasePage(BaseCompatActivity activity) {
        mContext = (T) activity;
        mRootView = initView();
        ButterKnife.bind(this, mRootView);
    }

    public abstract View initView();

    public void initData() {
    }

    public void onDestroy() {
    }

    public void showError(Context context, String error, int id) {
        if (id == 0) {
            ToastUtils.showToast(context, error + id);
        } else {
            ToastUtils.showToast(context, "错误码：" + id);
        }
    }
}
