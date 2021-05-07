package com.monk.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NoScrollViewPager extends ViewPager {

    /**
     * 是否禁止左右滑动，true为禁止，false为不禁止
     */
    private boolean noScroll = true;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件拦截
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // false 不拦截子控件的事件；
        return !noScroll && super.onInterceptTouchEvent(ev);
    }

    /**
     * 重写此方法，禁用ViewPager的滑动
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return noScroll || super.onTouchEvent(ev);
    }

    /**
     * @param noScroll true为禁止，false为不禁止
     */
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }
}
