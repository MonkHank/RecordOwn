package com.monk.moduleviews.views

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.monk.commonutils.LogUtil
import com.monk.moduleviews.R

/**
 * @author monk
 * @date 15/2/10
 */
class SlideSelectView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null)
    : View(mContext, attrs) {
    private val tag = "SlideSelectView"

    /** 大圆半径 */
    private val RADIU_BIG = 15f

    /*** 线的高度 */
    private val HEIGHT_LINE = 3f

    /*** 线距离两头的边距 */
    private val MARGEN_LINE = RADIU_BIG * 8

    /*** 竖直线的数量 */
    private val countOfSmallLine: Int

    /*** 竖直线的横坐标 */
    private lateinit var linesX: FloatArray
    private val mPaint: Paint
    private val mTextPaint: TextPaint
    private var mHeight = 0f
    private var mWidth = 0f

    /*** 大圆的横坐标 */
    private var bigCircleX = 0f
    private val textSize: Float

    /*** 当前大球距离最近的位置 */
    private var currentPosition: Int

    /*** 小圆之间的间距 */
    private var distanceX = 0f

    // private String[] text4Rates={ "特小","小", "中","大", "超大" };
    private var text4Rates = arrayOf("特小", "小", "中", "大", "超大")

    /*** 依附效果实现 */
    private var valueAnimator: ValueAnimator? = null

    /*** 用于纪录松手后的大圆x坐标 */
    private var currentPositionX = 0f
    private var selectListener: onSelectListener? = null
    private val density: Float

    interface onSelectListener {
        fun onSelect(position: Int)
    }


    init {
        density = mContext.resources.displayMetrics.density
        val a = mContext.obtainStyledAttributes(attrs, R.styleable.SlideSelectView)
        countOfSmallLine = a.getInt(R.styleable.SlideSelectView_circleCount, 5)
        textSize = a.getInt(R.styleable.SlideSelectView_textSize, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 10f, resources.displayMetrics).toInt()).toFloat()
        a.recycle()
        mPaint = Paint()
        mPaint.color = Color.GRAY
        mPaint.isAntiAlias = true

//		textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());
        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        mTextPaint.color = Color.GRAY
        mTextPaint.textSize = textSize
        currentPosition = countOfSmallLine / 2
    }

    /**
     * 设置显示文本
     * @param strings
     */
    fun setString(strings: Array<String>) {
        text4Rates = strings
        /*
         if (countOfSmallLine != text4Rates.length) {
			throw new IllegalArgumentException("the count of small circle must be equal to the " +
					"text array length !");
		}
         */
        require(countOfSmallLine == text4Rates.size) {
            "the count of small circle must be equal to the " +
                    "text array length !"
        }
    }

    fun setOnSelectListener(listener: onSelectListener?) {
        selectListener = listener
    }

    fun setPosition(position: Int) {
        currentPosition = position
        //		invalidate();
    }

    override fun onDraw(canvas: Canvas) {
        //画水平线
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = HEIGHT_LINE
        canvas.drawLine(MARGEN_LINE, mHeight / 2, mWidth - MARGEN_LINE, mHeight / 2, mPaint)

        //画竖线
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = HEIGHT_LINE
        /*
        for(int i = 0; i< countOfSmallLine; i++){
			canvas.drawLine(linesX[i],mHeight/2, linesX[i],mHeight/2-12,mPaint);
		}
         */
        for (i in 0 until countOfSmallLine) {
            canvas.drawLine(linesX[i], mHeight / 2, linesX[i], mHeight / 2 - 12, mPaint)
        }

        //画大圆的默认位置
        canvas.drawCircle(bigCircleX, mHeight / 2, RADIU_BIG, mPaint)

        //画文字
        canvas.drawText(text4Rates[currentPosition], linesX[currentPosition], mHeight / 2 - RADIU_BIG - 20, mTextPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> if (event.x >= MARGEN_LINE && event.x <= mWidth - MARGEN_LINE) {
                bigCircleX = event.x
                val position = ((event.x - MARGEN_LINE) / (distanceX / 2)).toInt()
                currentPosition = (position + 1) / 2
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> if (event.x >= MARGEN_LINE && event.x <= mWidth - MARGEN_LINE) {    //防止滑出边界
                bigCircleX = event.x
                val position = ((event.x - MARGEN_LINE) / (distanceX / 2)).toInt()
                currentPosition = (position + 1) / 2 //更新当前位置
                invalidate()
                LogUtil.e(tag, "e.getX():" + event.x)
            }
            MotionEvent.ACTION_UP -> if (event.x >= MARGEN_LINE && event.x <= mWidth - MARGEN_LINE) { //防止滑出边界
                //当前位置距离最近的小竖线的距离
                val currentDistance = event.x - MARGEN_LINE - currentPosition * distanceX
                if (currentPosition == 0 && currentDistance < 0 || currentPosition == text4Rates.size - 1 && currentDistance > 0) {
                    if (null != selectListener) {
                        selectListener!!.onSelect(currentPosition)
                    }
                    LogUtil.v(tag, "e.getX():" + event.x)
                    return true
                }
                currentPositionX = bigCircleX
                valueAnimator = ValueAnimator.ofFloat(currentDistance)
                valueAnimator?.setInterpolator(AccelerateInterpolator())
                valueAnimator?.addUpdateListener(AnimatorUpdateListener { animation ->
                    val slideDistance = animation.animatedValue as Float
                    bigCircleX = currentPositionX - slideDistance
                    invalidate()
                })
                valueAnimator?.setDuration(100)
                valueAnimator?.start()
                if (null != selectListener) {
                    selectListener!!.onSelect(currentPosition)
                }
                LogUtil.d(tag, "e.getX():" + event.x)
            }
            else -> {
            }
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mHeight = h + 10 * density
        mWidth = w.toFloat()
        //计算每个小竖线的x坐标
        linesX = FloatArray(countOfSmallLine)
        distanceX = (mWidth - MARGEN_LINE * 2) / (countOfSmallLine - 1)
        for (i in 0 until countOfSmallLine) {
            linesX[i] = i * distanceX + MARGEN_LINE
        }
        bigCircleX = linesX[currentPosition]
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val screenSize = getScreenSize(mContext as Activity)
        var resultWidth: Int
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        if (widthMode == MeasureSpec.EXACTLY) {
            resultWidth = widthSize
        } else {
            resultWidth = screenSize[0]
            if (widthMode == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(widthSize, screenSize[0])
            }
        }
        var resultHeight: Int
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (heightMode == MeasureSpec.EXACTLY) {
            resultHeight = heightSize
        } else {
            resultHeight = (RADIU_BIG * 6).toInt()
            if (heightMode == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(heightSize, resultHeight)
            }
        }
        setMeasuredDimension(resultWidth, resultHeight)
    }

    companion object {
        private fun getScreenSize(activity: Activity): IntArray {
            val metrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(metrics)
            return intArrayOf(metrics.widthPixels, metrics.heightPixels)
        }
    }

}