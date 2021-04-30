package com.monk.moduleviews.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.monk.commonutils.LogUtil
import com.monk.moduleviews.R

/**
 * @author monk
 * @date 2019-01-24
 */
class PercentCircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val tag = "PercentCircleView"
    private val paintId: Int
    private val circlePaint: Paint
    private val backgroundPaint: Paint
    private val redPaint: Paint
    private val circlePaint3: Paint
    private val textPaint: TextPaint
    private val density: Float
    private var rectF: RectF? = null
    private var rectF2: RectF? = null
    private var rectF3: RectF? = null
    private var backgroundRadius = 0f
    private var circleX = 0f
    private var circleY = 0f
    private val staticLayout: StaticLayout

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.PercentCircleView)
        paintId = ta.getInteger(R.styleable.PercentCircleView_paintId, 0)
        ta.recycle()
        density = context.resources.displayMetrics.density
        textPaint = TextPaint()
        /* 样式设置
         * paint.setFakeBoldText(true)：设置是否为粗体文字
         * paint.setUnderlineText(true)：设置下划线
         * paint.setTextSkewX((float) -0.25)：设置字体水平倾斜度，普通斜体字是 -0.25
         * paint.setStrikeThruText(true)：设置带有删除线效果
         * paint.setTextScaleX(2)：设置水平拉伸，高度不会变
         */
        // 设置是否使用抗锯齿功能，如果使用，会导致绘图速度变慢
        textPaint.isAntiAlias = true
        textPaint.color = Color.BLUE
        textPaint.textSize = 14 * density
        // 设置文字对齐方式
        textPaint.textAlign = Paint.Align.CENTER
        /* 1、Paint.Style.FILL：填充内部
         * 2、Paint.Style.FILL_AND_STROKE：填充内部和描边
         * 3、Paint.Style.STROKE：仅描边
         */textPaint.style = Paint.Style.STROKE
        // 设置画笔宽度
        textPaint.strokeWidth = 1 * density
        backgroundPaint = Paint()
        backgroundPaint.isAntiAlias = true
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.strokeWidth = 1 * density
        backgroundPaint.color = Color.GRAY
        circlePaint = Paint()
        circlePaint.color = Color.GREEN
        circlePaint.strokeWidth = 2 * density
        redPaint = Paint()
        redPaint.isAntiAlias = true
        redPaint.color = Color.RED
        redPaint.strokeWidth = 2 * density
        redPaint.style = Paint.Style.STROKE
        circlePaint3 = Paint()
        circlePaint3.color = Color.GREEN
        circlePaint3.strokeWidth = 50 * density
        circlePaint3.style = Paint.Style.FILL

        // 文字的位置跟width有关系
        val text = "one \n No2"
        staticLayout = StaticLayout(text, textPaint, (100 * density).toInt(), Layout.Alignment.ALIGN_NORMAL, 1.2f, 1f, false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        LogUtil.i(tag, "onSizeChanged：$w\t$h\t$oldw\t$oldh")
        val height = height
        val measuredHeight = measuredHeight
        val width = width
        val measuredWidth = measuredWidth
        LogUtil.w(tag, "onSizeChanged：$height\t$measuredHeight\t$width\t$measuredWidth")
        val x = (getWidth() - (getHeight() shr 1) shr 1).toFloat()
        val y = (getHeight() shr 2).toFloat()

        // 圆弧的外切圆,left,top,right,bottom边离x、y轴的坐标
        rectF = RectF(0f, 200f, 400f, 300f)
        rectF2 = RectF(200f, 200f, 50f, 100f)
        circleX = (width shr 1).toFloat()
        circleY = (height shr 1).toFloat()
        backgroundRadius = 60 * density
        rectF3 = RectF(circleX - backgroundRadius, circleY - backgroundRadius, circleX + backgroundRadius, circleY + backgroundRadius)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        LogUtil.i(tag, "onLayout：$changed\t$left\t$top\t$right\t$bottom")
    }

    /**
     * 覆盖而画，和FrameLayout机制类似
     * @param canvas
     */
    override fun onDraw(canvas: Canvas) {
        LogUtil.w(tag, "canvas：$canvas")
        canvas.drawCircle(circleX, circleY, backgroundRadius, backgroundPaint)
        canvas.drawArc(rectF3!!, 0f, 180f, false, redPaint)

//        canvas.drawText(text, circleX , circleY, textPaint);
        canvas.drawArc(rectF!!, 0f, 90f, true, circlePaint)
        canvas.drawArc(rectF!!, 0f, 90f, false, redPaint)
        canvas.drawRect(rectF!!, backgroundPaint)
        canvas.drawPoint(400f, 100f, circlePaint3)
        canvas.drawRect(rectF2!!, backgroundPaint)
        canvas.drawOval(rectF2!!, redPaint)
        canvas.save()
        canvas.translate(circleX, circleY - 10 * density)
        staticLayout.draw(canvas)
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> LogUtil.i(tag, "x = $x\t y = $y")
            else -> {
            }
        }
        return true
    }
}