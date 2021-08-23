package com.monk.moduleviews.eventdispatch

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import com.monk.activity.base.BaseView
import com.monk.commonutils.LogUtil
import com.monk.moduleviews.ActViewsDetail
import java.lang.StringBuilder

/**
 * @author monk
 * @date 2019-06-04
 */
class AnimatorView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : BaseView<AnimatorView?>(context, attrs) {
    private var mPaint: Paint? = null
    private var mRadius = 50
    private var mAnimator: ValueAnimator? = null

    private var sb: StringBuilder?=null

    init {
        init()
        sb = (context as ActViewsDetail).sb
    }

    private fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = -0x6b1e09

//        mAnimator = ValueAnimator.ofInt(0, 100);
//        mAnimator = ValueAnimator.ofArgb(0xff94E1F7, 0xffF35519);
        val ballEvaluator = BallEvaluator()
        val ball = Ball(50, -0x6b1e09)
        val ballEnd = Ball(100, -0xcaae7)
        mAnimator = ValueAnimator.ofObject(ballEvaluator, ball, ballEnd)

        // LinearInterpolator BounceInterpolator  AnticipateOvershootInterpolator
        // OvershootInterpolator
        mAnimator?.interpolator = SinInter()
        mAnimator?.duration = 5000
        mAnimator?.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue
            LogUtil.v(tag, simpleName + "value=" + animatedValue)
            sb?.append("$simpleName onLayout\n")
            val ball = animatedValue as Ball
            mRadius = ball.r
            mPaint!!.color = ball.color

//                mPaint.setColor((Integer) animatedValue);
//                mRadius= (int) animatedValue;
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(100f, 100f)
        canvas.drawCircle(0f, 0f, mRadius.toFloat(), mPaint!!)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                LogUtil.e("ValueAnimatorView", "onTouchEvent: ")
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