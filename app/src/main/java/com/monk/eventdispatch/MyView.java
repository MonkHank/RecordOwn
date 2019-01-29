package com.monk.eventdispatch;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-01-11
 */
public class MyView extends View {
    private final String tag = "MyView";
    public MyView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.v(tag,"dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.v(tag,"onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.i(tag,"canvas = "+canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        LogUtil.i(tag,"canvas = "+canvas);
    }
}
