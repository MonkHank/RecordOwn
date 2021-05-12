package com.monk.moduleviews.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.monk.commonutils.LogUtil
import com.monk.moduleviews.R

/**
 * @author MonkHank
 * @date 2018-05-10.
 */
class WiperSwitch : View, OnTouchListener {
    private var bg_on: Bitmap? = null
    private var bg_pause: Bitmap? = null
    private var bg_off: Bitmap? = null
    private var slipper_pause: Bitmap? = null
    private var slipper_white: Bitmap? = null
    // 当前滑块x
    /**
     * 0：bg_on.getWidth() - slipper_pause.getWidth()
     * 1：0
     * 2：bg_on.getWidth() / 3
     */
    private var nowX = 0

    // 0:on  1:pause  2:off
    private var nowStatus = -1
    private val ON = 0
    private val PAUSE = 1
    private val OFF = 2
    private var matrixs: Matrix? = null
    private var paint: Paint? = null
    private var listener: OnChangedListener? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        bg_off = BitmapFactory.decodeResource(resources, R.drawable.slide_switch_off)
        bg_on = BitmapFactory.decodeResource(resources, R.drawable.slide_switch_on)
        bg_pause = BitmapFactory.decodeResource(resources, R.drawable.slide_switch_stop)
        slipper_pause = BitmapFactory.decodeResource(resources, R.drawable.slide_dot)
        slipper_white = BitmapFactory.decodeResource(resources, R.drawable.slide_switch_block_30x90)
        setOnTouchListener(this)
        matrixs = Matrix()
        paint = Paint()
        nowX = bg_on?.width!! / 3
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (nowX < bg_on!!.width / 3) {
            canvas.drawBitmap(bg_off!!, matrix!!, paint) //画出关闭时候的背景
        } else if (nowX > bg_on!!.width / 3 * 2) {
            canvas.drawBitmap(bg_on!!, matrix!!, paint) //画出打开时候的背景
        } else {
            if (nowStatus == ON) {
                canvas.drawBitmap(bg_on!!, matrix!!, paint)
            } else if (nowStatus == OFF) {
                canvas.drawBitmap(bg_off!!, matrix!!, paint)
            } else if (nowStatus == PAUSE) {
                canvas.drawBitmap(bg_pause!!, matrix!!, paint) //画出暂停时候的背景
            }
        }
        // 对滑块滑动进行异常处理，不能让滑块出界
        if (nowX < 0) {
            nowX = 0
        } else if (nowX > bg_on!!.width - slipper_pause!!.width) {
            nowX = bg_on!!.width - slipper_pause!!.width
        }
        // 画出滑块
        LogUtil.v("WiperSwitch_onDraw", "nowX:" + nowX + "\tbg_on.getWidth():" + bg_on!!.width
                + "\tslipper_pause.getWidth():" + slipper_pause!!.width)
        when (nowStatus) {
            -1 -> {
                canvas.drawBitmap(bg_pause!!, matrix!!, paint) //画出暂停时候的背景
                canvas.drawBitmap(slipper_white!!, (bg_off!!.width / 3).toFloat(), 0f, paint)
            }
            ON, OFF -> canvas.drawBitmap(slipper_white!!, nowX.toFloat(), 0f, paint)
            PAUSE -> canvas.drawBitmap(slipper_pause!!, (bg_off!!.width / 3).toFloat(), 0f, paint)
        }
        if (action == MotionEvent.ACTION_UP) {
            if (nowX < bg_on!!.width / 3) {
                nowX += 10
                if (nowX > bg_on!!.width / 3) nowX = bg_on!!.width / 3
                postInvalidateDelayed(10)
            } else if (nowX > bg_on!!.width / 3) {
                nowX -= 10
                if (nowX < bg_on!!.width / 3) nowX = bg_on!!.width / 3
                postInvalidateDelayed(10)
            }
        }
    }

    private var action = 0
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        action = event.action
        val x = event.x
        val y = event.y
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                nowX = if (x > bg_on!!.width || y > bg_on!!.height) {
                    return false
                } else {
                    x.toInt()
                }
                LogUtil.e("ACTION_UP", "downX:$x")
                obtainStatus(x)
            }
            MotionEvent.ACTION_MOVE -> {
                nowX = x.toInt()
                LogUtil.i("ACTION_UP", "moveX:$x")
                obtainStatus(x)
            }
            MotionEvent.ACTION_UP -> {
                LogUtil.v("ACTION_UP", "nowStatus:$nowStatus")
                if (listener != null) listener!!.onChanged(this, nowStatus)
            }
        }
        invalidate()
        return true
    }

    private fun obtainStatus(x: Float) {
        if (x >= bg_on!!.width / 3 * 2) { // on 0
//                    nowX = bg_on.getWidth() - slipper_pause.getWidth();
            nowStatus = ON
        } else if (x < bg_on!!.width / 3) { // off 2
            nowX = 0
            nowStatus = OFF
        } else if (x > bg_on!!.width / 3 && x < bg_on!!.width / 3 * 2) { //pause 2
//                    nowX = bg_on.getWidth() / 3;
            nowStatus = PAUSE
        }
    }

    // 设置滑动开关的初始状态，供外部调用
    // 暂时先不需要设置nowX
    fun setNowStatus(status: Int) {
        when (status) {
            0 -> {
            }
            2 -> {
            }
            1 -> {
            }
        }
    }

    // 回调接口
    interface OnChangedListener {
        fun onChanged(wiperSwitch: WiperSwitch?, status: Int)
    }

    fun setOnChangedListener(listener: OnChangedListener?) {
        this.listener = listener
    }
}