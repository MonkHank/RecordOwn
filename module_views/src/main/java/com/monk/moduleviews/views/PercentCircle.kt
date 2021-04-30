package com.monk.moduleviews.views

import android.content.Context
import android.text.TextPaint
import android.graphics.RectF
import android.text.StaticLayout
import android.view.View.MeasureSpec
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.util.AttributeSet
import android.view.View
import com.monk.moduleviews.R

/**
 * @author MonkeyHank
 * @date 2017-10-25 09:10
 */
class PercentCircle(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var mTextPaint: TextPaint? = null
    private var mBackgroundPaint: Paint? = null
    private var mRingPaint: Paint? = null
    private var mCircleX = 0
    private var mCircleY = 0
    private var mArcRectF: RectF? = null
    private var mStartSweepValue = 0f
    private var mTargetPercent = 0f
    private val mDefaultRadius = 60
    private var mRadius: Int
    private val mDefaultBackgroundColor = -0x504b25
    private val mBackgroundColor: Int
    private val mDefaultRingColor = -0x96af5f
    private val mRingColor: Int
    private var mTextSize = 0
    private val mDefaultTextColor = -0x1
    private val mTextColor: Int
    private val density: Float = context.resources.displayMetrics.density
    private var staticLayout: StaticLayout? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PercentCircle)
        mRadius = typedArray.getInt(R.styleable.PercentCircle_radius_integer, mDefaultRadius)
        mBackgroundColor = typedArray.getColor(R.styleable.PercentCircle_background_color, mDefaultBackgroundColor)
        mTextColor = typedArray.getColor(R.styleable.PercentCircle_text_color, mDefaultTextColor)
        mRingColor = typedArray.getColor(R.styleable.PercentCircle_ring_color, mDefaultRingColor)

        // Be sure to call recycle() when done with them
        typedArray.recycle()
        init(context)
    }

    private fun init(context: Context) {
        //圆环开始角度 -90° 正北方向
        mStartSweepValue = -90f
        mBackgroundPaint = Paint()
        mBackgroundPaint!!.isAntiAlias = true
        mBackgroundPaint!!.color = mBackgroundColor
        mBackgroundPaint!!.style = Paint.Style.STROKE
        mTextPaint = TextPaint()
        mTextPaint!!.color = mTextColor
        mTextPaint!!.isAntiAlias = true
        mTextPaint!!.style = Paint.Style.FILL
        mTextPaint!!.strokeWidth = (0.025 * mRadius).toFloat()
        //文字大小为半径的一半 mRadius/2
        mTextPaint!!.textSize = 14 * density
        mTextPaint!!.textAlign = Paint.Align.CENTER

        //设置外圆环的画笔
        mRingPaint = Paint()
        mRingPaint!!.isAntiAlias = true
        mRingPaint!!.color = mRingColor
        mRingPaint!!.style = Paint.Style.STROKE
        mRingPaint!!.strokeWidth = (0.075 * mRadius).toFloat()

        //获得文字的字号 因为要设置文字在圆的中心位置
        mTextSize = mTextPaint!!.textSize.toInt()
        val text = """
            任务完成
            ${mTargetPercent.toInt()}%
            """.trimIndent()
        staticLayout = StaticLayout(text, mTextPaint, (150 * density).toInt(), Layout.Alignment.ALIGN_NORMAL, 1.2f, 1.0f, true)
    }

    /**
     * 主要是测量wrap_content时候的宽和高，因为宽高一样，只需要测量一次宽即可，高等于宽
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(widthMeasureSpec))
    }

    /**
     * 当wrap_content的时候，view的大小根据半径大小改变，但最大不会超过屏幕
     * @param measureSpec
     */
    private fun measure(measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            // 如果测量模式是MatchParent 或者精确值，则宽为测量的宽
            result = specSize
        } else {
            result = (mRadius * density + mRingPaint!!.strokeWidth * density).toInt()
            if (specMode == MeasureSpec.AT_MOST) {
                //如果测量模式是WrapContent ，则宽为 直径值 与 测量宽中的较小值；
                //否则当直径大于测量宽时，会绘制到屏幕之外；
                result = Math.min(result, specSize)
            }
        }
        return result
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //1、如果半径大于圆心的横坐标，需要手动缩小半径的值，否则画到屏幕之外；
        //2、改变了半径，则需要重新设置字体大小；
        //3、改变了半径，则需要重新设置外圆环的宽度
        //4、画背景圆的外接矩形，用来画圆环；
        mCircleX = measuredWidth / 2
        mCircleY = measuredHeight / 2
        if (mRadius > mCircleX) {
            mRadius = mCircleX
            mRadius = (mCircleX - 0.075 * mRadius).toInt()
            mTextPaint!!.strokeWidth = (0.025 * mRadius).toFloat()
            mTextPaint!!.textSize = (mRadius / 3).toFloat()
            mRingPaint!!.strokeWidth = (0.075 * mRadius).toFloat()
            mTextSize = mTextPaint!!.textSize.toInt()
        }
        mArcRectF = RectF((mCircleX - mRadius).toFloat(), (mCircleY - mRadius).toFloat(), (mCircleX + mRadius).toFloat(), (mCircleY + mRadius).toFloat())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {}
    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(mCircleX.toFloat(), mCircleY.toFloat(), mRadius.toFloat(), mBackgroundPaint!!)
        canvas.save()
        canvas.translate(mCircleX.toFloat(), (mCircleY / 2 + 10).toFloat())
        staticLayout!!.draw(canvas)
        canvas.restore()
        canvas.drawArc(mArcRectF!!, mStartSweepValue, (mTargetPercent.toInt() * 3.6).toFloat(), false, mRingPaint!!)

//        if(mCurrentPercent < mTargetPercent){
        //当前百分比+1
//            mCurrentPercent+=1;
        //当前角度+360
//            mCurrentAngle+=3.6;
//            postInvalidate();
        //每10ms重画一次
//            postInvalidateDelayed(10);
//        }
    }

    fun setTargetPercent(targetPercent: Float) {
        mTargetPercent = targetPercent
        postInvalidate()
    }

    fun setRadius(radius: Int) {
        mRadius = radius
    }

}