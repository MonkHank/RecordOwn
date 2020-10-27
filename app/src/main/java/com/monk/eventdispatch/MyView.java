package com.monk.eventdispatch;

import android.content.Context;
import android.graphics.Canvas;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-01-11
 */
public class MyView extends AppCompatButton {
    private final String tag = "AppCompatButton";
    private final String simpleName = "MyView：";
    private final Scroller mScroller;
    private boolean isValidToggle;
    private int mLastX;
    private boolean isOpen;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText("按钮");
        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtil.i(tag, simpleName + "onFinishInflate()");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        LogUtil.i(tag, simpleName + "onWindowFocusChanged()-" + hasWindowFocus);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(tag,simpleName+"onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.i(tag,simpleName+"onSizeChanged");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.i(tag,simpleName+"onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtil.i(tag, "MyView：canvas = " + canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtil.e(tag,simpleName+"onDetachedFromWindow");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.v(tag, simpleName + "dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.v(tag, "MyView：onTouchEvent");
        int x = (int) event.getX();
        isValidToggle = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = mLastX - x;
                //边界检测判断，防止滑块越界
                if (deltaX + getScrollX() > 0) {
                    scrollTo(0, 0);
                    return true;
                } else if (deltaX + getScrollX() + getMeasuredWidth() / 2 < 0) {
                    scrollTo(-getMeasuredWidth() / 2, 0);
                    return true;
                }
                scrollBy(deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                //处理弹性滑动
                smoothScroll();
                break;
            default:
                break;
        }
        mLastX = x;
        return super.onTouchEvent(event);
    }


    private void smoothScroll() {
        int deltaX = 0;
        if (getScrollX() < -getMeasuredWidth() / 4) {
            deltaX = -getScrollX() - getMeasuredWidth() / 2;
            if (!isOpen) {
                isOpen = true;
                isValidToggle = true;
            }
        }

        if (getScrollX() >= -getMeasuredWidth() / 4) {
            deltaX = -getScrollX();
            if (isOpen) {
                isOpen = false;
                isValidToggle = true;
            }
        }
        mScroller.startScroll(getScrollX(), 0, deltaX, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
//        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            postInvalidate();
//        }

        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
