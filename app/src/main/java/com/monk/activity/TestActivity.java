package com.monk.activity;

import com.monk.activity.base.BaseActivity;
import com.monk.aidldemo.R;

/**
 * @author monk
 * @date 2020/1/13
 */
public class TestActivity extends BaseActivity {
    @Override
    protected int initLayoutResId() {
        return R.layout.activity_test_linechart;
    }

    @Override
    protected void initData() {
        initToolbar();
    }
}
