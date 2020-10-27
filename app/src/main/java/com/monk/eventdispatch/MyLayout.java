package com.monk.eventdispatch;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2018-12-24.
 */
public class MyLayout extends RelativeLayout implements GestureDetector.OnDoubleTapListener,GestureDetector.OnGestureListener {
    private String tag = "RelativeLayout";
    private String simpleName = "MyLayout：";

    private GestureDetector mGestureDetector;
    private int width;
    private int height;

    public MyLayout(Context context) {
        this(context,null);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(getContext(), this);
        mGestureDetector.setOnDoubleTapListener(this);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        LogUtil.i(tag,simpleName+"构造函数：density="+ dm.density+"\tdensityDpi:"+dm.densityDpi);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(tag,simpleName+"onMeasure");
    }

    /**
     * setFrame 的时候会调用该函数;
     * getMeasuredWidth()：获取的是 mMeasuredWidth 的这个值，测量阶段结束之后，view 真实的值；
     * 而且这个值会在调用了setMeasuredDimensionRaw（）函数之后会被设置。所以getMeasuredWidth（）
     * 的值是 measure 阶段结束之后得到的view的原始的值。
     * getWidth()：获取的是 mRight-mLeft，在 setFrame 的时候计算，也就是 layout后的值；
     *
     * 那么最终 view 的大小取决两块，第一个测量，第二个摆放
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.v(tag,simpleName+"onSizeChanged：width="+getWidth()+" \t height="+getHeight());
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.i(tag,simpleName+"onLayout");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtil.e(tag,simpleName+"onDetachedFromWindow");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.e(tag,"MyLayout：onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.e(tag,"MyLayout：dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return mGestureDetector.onTouchEvent(event);
        LogUtil.e(tag,"MyLayout：onTouchEvent");
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (x < width / 2 && y < height / 2) {
                scrollBy(5,5);
            } else if (x > width / 2 && y < height / 2) {
                scrollBy(-10,0);
            } else if (x < width / 2 && y > height / 2) {
                scrollBy(0,-5);
            }else {
                scrollBy(-5,-5);
            }

        }
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        LogUtil.e("gesture","MyLayout：onSingleTapConfirmed:");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        LogUtil.e("gesture","MyLayout：onDoubleTap:");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        LogUtil.e("gesture","MyLayout：onDoubleTapEvent:");
        return false;
    }

    /******************** GestureListener **************************/
    @Override
    public boolean onDown(MotionEvent e) {
        LogUtil.i("gesture","MyLayout：onDown:");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        LogUtil.i("gesture","MyLayout：onShowPress:");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        LogUtil.i("gesture","MyLayout：onSingleTapUp:");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        LogUtil.i("gesture","MyLayout：onScroll:"+distanceX +"\t"+distanceY);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        LogUtil.i("gesture","MyLayout：onLongPress:");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        LogUtil.i("gesture","MyLayout：onScroll:"+velocityX +"\t"+velocityY);
        return false;
    }
}
