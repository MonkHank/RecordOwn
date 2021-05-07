package com.monk.moduleviews.eventdispatch

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AnticipateOvershootInterpolator
import com.monk.activity.base.BaseView

/**
 * @author monkey
 * @date 2019-6-5 11:03:39
 */
class AnimatorPathView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : BaseView<AnimatorPathView?>(context, attrs) {
    private var mPaint: Paint? = null
    private var mPath: Path? = null
    private var pathMeasure: PathMeasure? = null

    companion object {
        private const val TAG = "AnimatorView"

        /**
         * n角星路径
         *
         * @param num 几角星
         * @param R   外接圆半径
         * @param r   内接圆半径
         * @return n角星路径
         */
        fun nStarPath(path: Path, num: Int, R: Float, r: Float): Path {
            val perDeg = (360 / num).toFloat()
            val degA = perDeg / 2 / 2
            val degB = 360 / (num - 1) / 2 - degA / 2 + degA
            path.moveTo((Math.cos(rad(degA).toDouble()) * R).toFloat(), (-Math.sin(rad(degA).toDouble()) * R).toFloat())
            for (i in 0 until num) {
                path.lineTo(
                        (Math.cos(rad(degA + perDeg * i).toDouble()) * R).toFloat(),
                        (-Math.sin(rad(degA + perDeg * i).toDouble()) * R).toFloat())
                path.lineTo(
                        (Math.cos(rad(degB + perDeg * i).toDouble()) * r).toFloat(),
                        (-Math.sin(rad(degB + perDeg * i).toDouble()) * r).toFloat())
            }
            path.close()
            return path
        }

        /**
         * 角度制化为弧度制
         *
         * @param deg 角度
         * @return 弧度
         */
        fun rad(deg: Float): Float {
            return (deg * Math.PI / 180).toFloat()
        }
    }

    init {
        init()
    }

    private fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = -0x6b1e09
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = 4f
        mPaint!!.strokeJoin = Paint.Join.ROUND
        //测量路径
        mPath = Path()
        mPath = nStarPath(mPath!!, 8, 50f, 20f) //八角形路径
        pathMeasure = PathMeasure(mPath, false)
    }

    private fun createAnimator(): ValueAnimator {
        val mAnimator = ValueAnimator.ofInt(0, 800)
        mAnimator.repeatCount = 1
        mAnimator.repeatMode = ValueAnimator.REVERSE
        mAnimator.duration = 3000
        mAnimator.interpolator = AnticipateOvershootInterpolator()
        mAnimator.addUpdateListener { animation ->
            val value = animation.animatedFraction
            //核心:创建DashPathEffect
            val effect = DashPathEffect(floatArrayOf(
                    pathMeasure!!.length,
                    pathMeasure!!.length),
                    value * pathMeasure!!.length)
            mPaint!!.pathEffect = effect
            invalidate()
        }
        return mAnimator
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(100f, 100f)
        canvas.drawPath(mPath!!, mPaint!!)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> createAnimator().start()
            MotionEvent.ACTION_UP -> {
            }
        }
        return super.onTouchEvent(event)
    }

}