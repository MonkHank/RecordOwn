package com.monk.moduleviews.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by bgl on 2017/5/16.
 */
class TopNewsViewPager : ViewPager {
    private var startX = 0
    private var startY = 0
    private var endY = 0
    private var endX = 0

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    /**
     * 1、上下滑动需要拦截
     * 2、向右滑动并且是第一个界面需要拦截
     * 3、向左滑动并且是最后一个界面需要拦截
     *
     * @param ev
     * @return
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        //请求所有父控件及祖宗控件不要拦截事件
        parent.requestDisallowInterceptTouchEvent(true)
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                endX = ev.x.toInt()
                endY = ev.y.toInt()
                val dx = endX - startX
                val dy = endY - startY
                val currentItem = currentItem
                if (Math.abs(dy) < Math.abs(dx)) {
                    // 右滑动
                    if (dx > 0) {
                        // 向右滑,需要拦截
                        if (currentItem == 0) {
                            // 第一个页面
                            parent.requestDisallowInterceptTouchEvent(false)
                        }
                    } else {
                        // 向左滑
                        val count = adapter!!.count
                        if (currentItem == count - 1) {
                            // 最后一个页面,需要拦截
                            parent.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                } else {
                    // 上下滑动,需要拦截
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}