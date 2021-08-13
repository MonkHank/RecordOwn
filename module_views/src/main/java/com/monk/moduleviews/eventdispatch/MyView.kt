package com.monk.moduleviews.eventdispatch

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Scroller
import androidx.appcompat.widget.AppCompatButton
import com.monk.ActViewsDetail
import com.monk.commonutils.LogUtil
import java.lang.StringBuilder

/**
 * @author monk
 * @date 2019-01-11
 */
class MyView(context: Context?, attrs: AttributeSet?) : AppCompatButton(context!!, attrs) {
    private val tag = "AppCompatButton"
    private val simpleName = "MyView："
    private val mScroller: Scroller
    private var isValidToggle = false
    private var mLastX = 0
    private var isOpen = false

    private var sb:StringBuilder?=null

    init {
        text = "按钮"
        mScroller = Scroller(context)
        sb = (context as ActViewsDetail).sb
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        LogUtil.i(tag, simpleName + "onFinishInflate()")
        sb?.append("$simpleName onFinishInflate()")
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        LogUtil.i(tag, simpleName + "onWindowFocusChanged()-" + hasWindowFocus)
        sb?.append("$simpleName onWindowFocusChanged() $hasWindowFocus")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        LogUtil.i(tag, simpleName + "onMeasure")
        sb?.append("$simpleName onMeasure\n")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LogUtil.i(tag, simpleName + "onSizeChanged")
        sb?.append("$simpleName onSizeChanged\n")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        LogUtil.i(tag, simpleName + "onLayout")
        sb?.append("$simpleName onLayout\n")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //        LogUtil.i(tag, "MyView：canvas = " + canvas);
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        LogUtil.e(tag, simpleName + "onDetachedFromWindow")
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        LogUtil.v(tag, simpleName + "dispatchTouchEvent")
        sb?.append("$simpleName dispatchTouchEvent\n")
        return super.dispatchTouchEvent(event)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        LogUtil.v(tag, "MyView：onTouchEvent")
        sb?.append("$simpleName onTouchEvent ${event.action}\n")
        val x = event.x.toInt()
        isValidToggle = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> if (!mScroller.isFinished) {
                mScroller.abortAnimation()
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = mLastX - x
                //边界检测判断，防止滑块越界
                if (deltaX + scrollX > 0) {
                    scrollTo(0, 0)
                    return true
                } else if (deltaX + scrollX + measuredWidth / 2 < 0) {
                    scrollTo(-measuredWidth / 2, 0)
                    return true
                }
                scrollBy(deltaX, 0)
            }
            MotionEvent.ACTION_UP ->                 //处理弹性滑动
                smoothScroll()
            else -> {
            }
        }
        mLastX = x
        return super.onTouchEvent(event)
    }

    private fun smoothScroll() {
        var deltaX = 0
        if (scrollX < -measuredWidth / 4) {
            deltaX = -scrollX - measuredWidth / 2
            if (!isOpen) {
                isOpen = true
                isValidToggle = true
            }
        }
        if (scrollX >= -measuredWidth / 4) {
            deltaX = -scrollX
            if (isOpen) {
                isOpen = false
                isValidToggle = true
            }
        }
        mScroller.startScroll(scrollX, 0, deltaX, 0, 500)
        invalidate()
    }

    override fun computeScroll() {
//        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            postInvalidate();
//        }
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }


}