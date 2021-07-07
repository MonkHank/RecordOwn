package com.monk.moduleviews.eventdispatch

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.OnDoubleTapListener
import android.view.MotionEvent
import android.widget.RelativeLayout
import com.monk.moduleviews.ViewsDetailActivity
import com.monk.commonutils.LogUtil
import java.lang.StringBuilder

/**
 * @author monk
 * @date 2018-12-24.
 */
class MyLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr),
        OnDoubleTapListener,
        GestureDetector.OnGestureListener {

    private val tag = "MyLayout"
    private val simpleName = "MyLayout："
    private val mGestureDetector: GestureDetector = GestureDetector(getContext(), this)
    private var widths = 0
    private var heights = 0

    private var sb: StringBuilder?=null

    init {
        mGestureDetector.setOnDoubleTapListener(this)
        val dm = context.resources.displayMetrics
        LogUtil.i(tag, simpleName + "构造函数：density=" + dm.density + "densityDpi:" + dm.densityDpi)
        sb = (context as ViewsDetailActivity).sb
        sb?.append("$simpleName 构造函数:density=${dm.density}\tdensityDpi:${dm.densityDpi}\n")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        LogUtil.i(tag, simpleName + "onMeasure")
        sb?.append("$simpleName onMeasure\n")
    }

    /**
     * setFrame 的时候会调用该函数;
     * getMeasuredWidth()：获取的是 mMeasuredWidth 的这个值，测量阶段结束之后，view 真实的值；
     * 而且这个值会在调用了setMeasuredDimensionRaw（）函数之后会被设置。所以getMeasuredWidth（）
     * 的值是 measure 阶段结束之后得到的view的原始的值。
     * getWidth()：获取的是 mRight-mLeft，在 setFrame 的时候计算，也就是 layout后的值；
     *
     * 那么最终 view 的大小取决两块，第一个测量，第二个摆放
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LogUtil.v(tag, simpleName + "onSizeChanged：width=" + getWidth() + " \t height=" + getHeight())
        sb?.append("$simpleName onSizeChanged width=$width height=$heights\n")
        widths = width
        heights = height
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        LogUtil.i(tag, simpleName + "onLayout")
        sb?.append("$simpleName onLayout\n")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        LogUtil.e(tag, simpleName + "onDetachedFromWindow")
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        LogUtil.e(tag, "MyLayout：onInterceptTouchEvent")
        sb?.append("$simpleName onInterceptTouchEvent\n")
        return super.onInterceptTouchEvent(ev)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        LogUtil.e(tag, "MyLayout：dispatchTouchEvent")
        sb?.append("$simpleName dispatchTouchEvent\n")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return mGestureDetector.onTouchEvent(event);
        LogUtil.e(tag, "MyLayout：onTouchEvent")
        sb?.append("$simpleName onTouchEvent ${event.action}\n")
        val x = event.x
        val y = event.y
        if (event.action == MotionEvent.ACTION_MOVE) {
            if (x < widths / 2 && y < heights / 2) {
                scrollBy(5, 5)
            } else if (x > widths / 2 && y < heights / 2) {
                scrollBy(-10, 0)
            } else if (x < widths / 2 && y > heights / 2) {
                scrollBy(0, -5)
            } else {
                scrollBy(-5, -5)
            }
        }
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        LogUtil.e("gesture", "MyLayout：onSingleTapConfirmed:")
        sb?.append("$simpleName onSingleTapConfirmed\n")
        return false
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        LogUtil.e("gesture", "MyLayout：onDoubleTap:")
        sb?.append("$simpleName onDoubleTap\n")
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        LogUtil.e("gesture", "MyLayout：onDoubleTapEvent:")
        sb?.append("$simpleName onDoubleTapEvent\n")
        return false
    }

    /******************** GestureListener  */
    override fun onDown(e: MotionEvent): Boolean {
        LogUtil.i("gesture", "MyLayout：onDown:")
        sb?.append("$simpleName onDown\n")
        return true
    }

    override fun onShowPress(e: MotionEvent) {
        LogUtil.i("gesture", "MyLayout：onShowPress:")
        sb?.append("$simpleName onShowPress\n")
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        LogUtil.i("gesture", "MyLayout：onSingleTapUp:")
        sb?.append("$simpleName onSingleTapUp\n")
        return false
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        LogUtil.i("gesture", "MyLayout：onScroll:$distanceX\t$distanceY")
        sb?.append("$simpleName onScroll\n")
        return false
    }

    override fun onLongPress(e: MotionEvent) {
        LogUtil.i("gesture", "MyLayout：onLongPress:")
        sb?.append("$simpleName onLongPress\n")
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        LogUtil.i("gesture", "MyLayout：onScroll:$velocityX\t$velocityY")
        sb?.append("$simpleName onFling\n")
        return false
    }

}