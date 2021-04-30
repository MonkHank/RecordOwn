package com.monk.moduleviews.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText

/**
 * 防止在scrollView中无法上下滑动的情况
 * @author MonkHank
 * @date 2018-04-08.
 */
class ScrollEditText(context: Context?, attrs: AttributeSet?) : AppCompatEditText(context!!, attrs) {
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 请求scrollView不要拦截我的事件
        parent.requestDisallowInterceptTouchEvent(true)
        return super.onTouchEvent(event)
    }
}