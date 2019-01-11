package com.monk.eventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.monk.LogUtil;

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
}
