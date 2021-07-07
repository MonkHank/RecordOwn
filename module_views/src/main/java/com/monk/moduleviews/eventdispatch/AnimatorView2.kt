package com.monk.moduleviews.eventdispatch

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import com.monk.moduleviews.ViewsDetailActivity
import com.monk.activity.base.BaseView
import com.monk.commonutils.LogUtil
import java.lang.StringBuilder

/**
 * @author monk
 * @date 2019-06-05
 */
class AnimatorView2 @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : BaseView<AnimatorView2?>(context, attrs) {
    private var mPaint: Paint? = null
    private val mRadius = 50
    private var mAnimator: ObjectAnimator? = null
    private var rectF: RectF? = null

    private var sb: StringBuilder?=null

    init {
        init()
        sb = (context as ViewsDetailActivity).sb
    }

    private fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = -0x6b1e09
        rectF = RectF(0f, 50f, 100f, 0f)

        // 常用属性
        // alpha translationX translationY rotation rotationX rotationY scaleX scaleY
        mAnimator = ObjectAnimator.ofFloat(this, "rotationX", 0f, 360f)
        mAnimator?.duration = 2000
        mAnimator?.repeatCount = ValueAnimator.INFINITE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(100f, 100f)
        //        canvas.drawCircle(0, 0, mRadius, mPaint);
        canvas.drawRect(rectF!!, mPaint!!)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                LogUtil.e(tag, "onTouchEvent: ")
                sb?.append("$simpleName onLayout\n")
                mAnimator!!.start() //点击开启动画
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // 不取消，则 animator在view detached后任然在执行
        mAnimator!!.cancel()
    }

}