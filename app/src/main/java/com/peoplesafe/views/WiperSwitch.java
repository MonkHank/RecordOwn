package com.peoplesafe.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;


/**
 * @author MonkHank
 * @date 2018-05-10.
 */
public class WiperSwitch extends View implements View.OnTouchListener {

    private Bitmap bg_on, bg_pause, bg_off, slipper_pause, slipper_white;
    // 当前滑块x
    /**
     * 0：bg_on.getWidth() - slipper_pause.getWidth()
     * 1：0
     * 2：bg_on.getWidth() / 3
     */
    private int nowX;

    // 0:on  1:pause  2:off
    private int nowStatus = -1;
    private final int ON=0;
    private final int PAUSE=1;
    private final int OFF=2;

    private Matrix matrix;
    private Paint paint;

    private OnChangedListener listener;

    public WiperSwitch(Context context) {
        super(context);
        init();
    }

    public WiperSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bg_off = BitmapFactory.decodeResource(getResources(), R.drawable.slide_switch_off);
        bg_on = BitmapFactory.decodeResource(getResources(), R.drawable.slide_switch_on);
        bg_pause = BitmapFactory.decodeResource(getResources(), R.drawable.slide_switch_stop);
        slipper_pause = BitmapFactory.decodeResource(getResources(), R.drawable.slide_dot);
        slipper_white = BitmapFactory.decodeResource(getResources(), R.drawable.slide_switch_block_30x90);
        setOnTouchListener(this);
        matrix = new Matrix();
        paint = new Paint();
        nowX = bg_on.getWidth() / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (nowX < (bg_on.getWidth() / 3)) {
            canvas.drawBitmap(bg_off, matrix, paint);//画出关闭时候的背景
        } else if (nowX > (bg_on.getWidth() / 3*2)) {
            canvas.drawBitmap(bg_on, matrix, paint);//画出打开时候的背景
        } else {
            if (nowStatus == ON) {
                canvas.drawBitmap(bg_on, matrix, paint);
            } else if (nowStatus == OFF) {
                canvas.drawBitmap(bg_off, matrix, paint);
            } else if (nowStatus == PAUSE) {
                canvas.drawBitmap(bg_pause, matrix, paint);//画出暂停时候的背景
            }
        }
        // 对滑块滑动进行异常处理，不能让滑块出界
        if (nowX < 0) {
            nowX = 0;
        } else if (nowX > bg_on.getWidth() - slipper_pause.getWidth()) {
            nowX = bg_on.getWidth() - slipper_pause.getWidth();
        }
        // 画出滑块
        LogUtil.v("WiperSwitch_onDraw", "nowX:" + nowX+"\tbg_on.getWidth():"+bg_on.getWidth()
                +"\tslipper_pause.getWidth():"+ slipper_pause.getWidth());
        switch (nowStatus) {
            case -1:
                canvas.drawBitmap(bg_pause, matrix, paint);//画出暂停时候的背景
                canvas.drawBitmap(slipper_white, bg_off.getWidth() / 3, 0, paint);
                break;
            case ON:
            case OFF:
                canvas.drawBitmap(slipper_white, nowX, 0, paint);
                break;
            case PAUSE:
                canvas.drawBitmap(slipper_pause, bg_off.getWidth() / 3, 0, paint);
                break;
        }
        if (action == MotionEvent.ACTION_UP) {
            if (nowX < bg_on.getWidth() / 3) {
                nowX += 10;
                if (nowX > bg_on.getWidth() / 3)
                    nowX = bg_on.getWidth() / 3;
                postInvalidateDelayed(10);
            } else if (nowX > bg_on.getWidth() / 3) {
                nowX -= 10;
                if (nowX < bg_on.getWidth() / 3)
                    nowX = bg_on.getWidth() / 3;
                postInvalidateDelayed(10);
            }
        }
    }

    private int action;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (x > bg_on.getWidth() || y > bg_on.getHeight()) {
                    return false;
                } else {
                    nowX = (int) x;
                }
                LogUtil.e("ACTION_UP","downX:"+x);
                obtainStatus(x);
                break;
            case MotionEvent.ACTION_MOVE:
                nowX = (int) x;
                LogUtil.i("ACTION_UP","moveX:"+x);
                obtainStatus(x);
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.v("ACTION_UP", "nowStatus:" + nowStatus);
                if (listener != null)
                    listener.onChanged(this, nowStatus);
                break;
        }
        invalidate();

        return true;
    }

    private void obtainStatus(float x) {
        if (x >= bg_on.getWidth() / 3 *2) {// on 0
//                    nowX = bg_on.getWidth() - slipper_pause.getWidth();
            nowStatus = ON;
        } else if (x < bg_on.getWidth() / 3) {// off 2
            nowX = 0;
            nowStatus = OFF;
        } else if (x>bg_on.getWidth()/3&&x<bg_on.getWidth()/3*2){//pause 2
//                    nowX = bg_on.getWidth() / 3;
            nowStatus = PAUSE;
        }
    }

    // 设置滑动开关的初始状态，供外部调用
    // 暂时先不需要设置nowX
    public void setNowStatus(int status) {
        switch (status) {
            case 0:// on
//                nowX = 0;
                break;
            case 2:// off
//                nowX = bg_on.getWidth() - slipper_pause.getWidth();
                break;
            case 1:// pause
//                nowX = bg_on.getWidth() / 3;
                break;
        }
    }

    // 回调接口
    public interface OnChangedListener {
        void onChanged(WiperSwitch wiperSwitch, int status);
    }

    public void setOnChangedListener(OnChangedListener listener) {
        this.listener = listener;
    }
}
