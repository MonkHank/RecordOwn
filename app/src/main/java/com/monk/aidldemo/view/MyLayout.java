package com.monk.aidldemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.monk.aidldemo.LogUtil;

/**
 * @author monk
 * @date 2018-12-24.
 */
public class MyLayout extends LinearLayout implements GestureDetector.OnDoubleTapListener,GestureDetector.OnGestureListener {

    private GestureDetector mGestureDetector;

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

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        LogUtil.e("gesture","onSingleTapConfirmed:");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        LogUtil.e("gesture","onDoubleTap:");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        LogUtil.e("gesture","onDoubleTapEvent:");
        return false;
    }

    /******************** GestureListener **************************/
    @Override
    public boolean onDown(MotionEvent e) {
        LogUtil.i("gesture","onDown:");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        LogUtil.i("gesture","onShowPress:");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        LogUtil.i("gesture","onSingleTapUp:");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        LogUtil.i("gesture","onScroll:"+distanceX +"\t"+distanceY);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        LogUtil.i("gesture","onLongPress:");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        LogUtil.i("gesture","onScroll:"+velocityX +"\t"+velocityY);
        return false;
    }
}
