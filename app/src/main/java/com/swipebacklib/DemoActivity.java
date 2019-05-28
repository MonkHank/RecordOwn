
package com.swipebacklib;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;
import com.monk.utils.ThreadManager;
import com.swipebacklib.app.SwipeBackActivity;


/**
 * @author hyh
 * @date 8/11/13.
 */
public class DemoActivity extends SwipeBackActivity implements View.OnClickListener {
    private static final int VIBRATE_DURATION = 20;

    private int[] mBgColors;

    private static int mBgIndex = 0;


    private RadioGroup mTrackingModeGroup;

    private SwipeBackLayout mSwipeBackLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipeback_demo);
        findViews();
        changeActionBarColor();
        mSwipeBackLayout = getSwipeBackLayout();

        //获取屏幕的宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int  phoneWidth  = dm.widthPixels ;
        LogUtil.e("tag","phoneWidth:"+phoneWidth);
        mSwipeBackLayout.setEdgeSize(phoneWidth/2);//滑动销毁触摸点到下边界的距离(相对于右滑退出)


        mTrackingModeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int edgeFlag;
            switch (checkedId) {
                case R.id.mode_left:
                    edgeFlag = SwipeBackLayout.EDGE_LEFT;
                    break;
                case R.id.mode_right:
                    edgeFlag = SwipeBackLayout.EDGE_RIGHT;
                    break;
                case R.id.mode_bottom:
                    edgeFlag = SwipeBackLayout.EDGE_BOTTOM;
                    break;
                default:
                    edgeFlag = SwipeBackLayout.EDGE_ALL;
            }
            mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag);
            saveTrackingMode(edgeFlag);
        });
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                vibrate(VIBRATE_DURATION);
            }

            @Override
            public void onScrollOverThreshold() {
                vibrate(VIBRATE_DURATION);
            }
        });

        ThreadManager.getThreadPool().execute(() -> {
//                Looper.prepare();
//                ToastUtils.showToast(DemoActivity.this, "测绘looper函数");
            // onResume()方法还没执行，不会检测是否在子线程哦
            ((Button)findViewById(R.id.btn_start)).setText("测试子线程更新UI");
//                Looper.loop();
        });
    }


    private void saveTrackingMode(int flag) {
//        PreferenceUtils.setPrefInt(getApplicationContext(), mKeyTrackingMode, flag);
    }


    private void changeActionBarColor() {
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColors()[mBgIndex]));
        mBgIndex++;
        if (mBgIndex >= getColors().length) {
            mBgIndex = 0;
        }
    }

    private void findViews() {
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_finish).setOnClickListener(this);
        mTrackingModeGroup = (RadioGroup) findViewById(R.id.tracking_mode);
    }

    private int[] getColors() {
        if (mBgColors == null) {
            Resources resource = getResources();
            mBgColors = new int[]{
                    0xff33B5E5,
                    0xffAA66CC,
                    0xff99CC00,
                    0xffFFBB33,
                    0xffFF4444,
            };
        }
        return mBgColors;
    }

    private void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(DemoActivity.this, DemoActivity.class));
                break;
            case R.id.btn_finish:
                scrollToFinishActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        LogUtil.e("DemoActivity","DemoActivity finish()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("DemoActivity","DemoActivity onDestroy()");
    }
}
