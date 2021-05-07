package com.monk.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoScrollViewPager : ViewPager {
    /**
     * 是否禁止左右滑动，true为禁止，false为不禁止
     */
    private var noScroll = true

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    /**
     * 事件拦截
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // false 不拦截子控件的事件；
        return !noScroll && super.onInterceptTouchEvent(ev)
    }

    /**
     * 重写此方法，禁用ViewPager的滑动
     *
     * @param ev
     * @return
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return noScroll || super.onTouchEvent(ev)
    }

    /**
     * @param noScroll true为禁止，false为不禁止
     */
    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }
}