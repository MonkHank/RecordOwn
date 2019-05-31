package com.monk.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;
import com.monk.commonutils.ToastUtils;

import butterknife.ButterKnife;

/**
 * @author monk
 * @date 2019-05-27
 */
public class BaseCompatActivity<T extends BaseCompatActivity> extends AppCompatActivity {

    protected String tag = BaseCompatActivity.class.getSimpleName();

    protected BaseCompatActivity<T> mContext;
    protected String simpleName;

    /**
     * 该方法会自动传入一个 Bundle 对象, 该 Bundle 对象就是上次被系统销毁时在 onSaveInstanceState 或者
     * onRestoreInstanceState 中保存的数据
     * -- 注意 : 只有是系统自动回收的时候才会保存 Bundle 对象数据；默认为null
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        simpleName = mContext.getClass().getSimpleName();
        LogUtil.i(tag, simpleName + "：onCreate(Bundle)-- "+savedInstanceState);

        fullScreen(mContext);
    }

    protected void initToolbar(int layoutResId) {
        setContentView(layoutResId);
        ButterKnife.bind(this);
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


    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }


    public void startActivity(Class<? extends BaseCompatActivity> cls) {
        startActivity(new Intent(mContext, cls));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.v(tag, simpleName + "：onNewIntent(Intent)");
    }

    /**
     * 官方建使用 onRestoreInstanceState() 来恢复数据而不是 onCreate()，
     * 所以在 onStart() 方法不要做其他事情
     */
    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i(tag, simpleName + "：onStart()");
    }

    /**
     * <p>
     * 方法回调时机 : 在 Activity 被系统销毁之后 恢复 Activity 时被调用, 只有销毁了之后重建的时候才调用,
     * 如果内存充足, 系统没有销毁这个 Activity, 就不需要调用;
     * </p>
     *
     * <p>
     * 横竖屏切换，不设置 android:configuration 时候，会执行这个方法
     * </p>
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.v(tag, simpleName + "：onRestoreInstanceState(Bundle)");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(tag, simpleName + "：onResume()");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogUtil.w(tag, simpleName + "：onWindowFocusChanged() -- " + hasFocus);
    }

    /**
     * 如果执行，那么是在 onPause()之前执行
     */
    @Override
    public void finish() {
        super.finish();
        LogUtil.e(tag, simpleName + "：finish()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e(tag, simpleName + "：onPause()");
    }

    /**
     * 数据保存 : Activity 声明周期结束的时候, 需要保存 Activity 状态的时候,
     * 会将要保存的数据使用键值对的形式 保存在 Bundle 对象中;
     * <p>
     * 恢复数据 : 在 Activity 的 onCreate()方法 创建 Activity 的时候会传入一个 Bundle 对象,
     * 这个 Bundle 对象就是这个 outState 参数;
     *
     * <p>
     * Activity 容易被销毁的时候调用, 注意是容易被销毁, 也可能没有销毁就调用了;
     * ①home键、②电源键、③启动其它Activity、④横竖屏切换；
     * 用户主动销毁不会调用，比如回退键，或者调用finish()；
     * 调用时机不固定，一定是在onStop()之前，但不确定是否在onPause()之前还是之后；
     * 只有有id的组件才会保存；
     * </p>
     *
     * <p>
     * 对于Fragment 来说，如果 commit() 函数在 onSaveInstance() 之后执行，那么会抛出异常
     * IllegalStateException: Can not perform this action after onSaveInstanceState
     * 避免方案：
     * - 不要把Fragment事务放在异步线程的回调中，比如不要把Fragment事务放在AsyncTask的onPostExecute()，
     * 因此 onPostExecute() 可能会在 onSaveInstanceState() 之后执行。
     * - 逼不得已时使用commitAllowingStateLoss()。
     * </p>
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 默认实现组件状态保存
        super.onSaveInstanceState(outState);
        LogUtil.v(tag, simpleName + "：onSaveInstanceState(Bundle)");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e(tag, simpleName + "：onStop()");
    }

    /**
     * onStop() 之后执行
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i(tag, simpleName + "：onRestart()");
    }


    /**
     * 执行不代表 finish() 函数也会执行
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.destroyExToast();
        ToastUtils.destroyToast();
        LogUtil.e(tag, simpleName + "：onDestroy()");
    }

    /**
     * Androidmanifest 配置了 android:configuration="orientation|screenSize"，再切换屏幕时候会执行该函数，
     * 其他生命周期函数不执行
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.v(tag, simpleName + "：onConfigurationChanged() -- " + newConfig.orientation);
    }
}
