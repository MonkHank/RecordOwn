package com.peoplesafe.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *  防止在scrollView中无法上下滑动的情况
 * @author MonkHank
 * @date 2018-04-08.
 */
public class ScrollEditText extends androidx.appcompat.widget.AppCompatEditText {

    public ScrollEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 请求scrollView不要拦截我的事件
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }
}
