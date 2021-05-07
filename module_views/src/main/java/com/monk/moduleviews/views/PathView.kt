package com.monk.moduleviews.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * path 的一些to方法
 * https://blog.csdn.net/a394268045/article/details/70792619
 * @author monk
 * @date 2020/1/14
 */
class PathView : View {
    private var mPaint: Paint? = null
    private var mPath: Path? = null

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        mPaint = Paint()
        mPaint!!.color = Color.RED
        mPaint!!.strokeWidth = 5f
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.STROKE // 如果不设置，默认是填充
        mPath = Path()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPath!!.quadTo(300f, 100f, 600f, 500f) // 贝塞尔曲线
        canvas.drawPath(mPath!!, mPaint!!)
    }
}