package com.monk.moduleviews.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.monk.commonutils.LogUtil

/**
 * @author monk
 * @date 2017/6/2.
 */
class QuickIndexBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val tag = "QuickIndexBar"
    private val density: Float
    private val indexArr = arrayOf("#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z")
    private var paint: Paint? = null
    private var cellHeight = 0f
    private var widths = 0

    /*** 记录上一次按下的字母索引 */
    private var lastIndex = -1
    private val mid_transparent = 0x11000000
    private val transparent = 0x00000000
    private var listener: OnTouchLetterListener? = null

    init {
        init()
        density = context.resources.displayMetrics.density
    }

    private fun init() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.textSize = 10 * density
        // 设置文本的起点是文字边框底边的中心
        paint!!.textAlign = Paint.Align.CENTER
        paint!!.style = Paint.Style.FILL
    }

    /**
     * onMeasure方法之后执行，onLayout方法之前执行
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widths = measuredWidth
        cellHeight = measuredHeight * 1.0f / indexArr.size
        LogUtil.v(this@QuickIndexBar, "自定义控件宽 = " + widths + "像素")
    }

    override fun onDraw(canvas: Canvas) {
        for (i in indexArr.indices) {
            val x = (widths shr 1).toFloat()
            val y = cellHeight / 2 + (getTextHeight(indexArr[i]) shr 1) + i * cellHeight
            if (lastIndex == i) {
                paint!!.color = Color.BLUE
                canvas.drawCircle(x, y - (getTextHeight(indexArr[i]) shr 1), ((18 shr 1) + 4).toFloat(), paint!!)
                paint!!.color = Color.WHITE
            } else {
                paint!!.color = Color.BLACK
            }
            canvas.drawText(indexArr[i], x, y, paint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                setBackgroundColor(mid_transparent)
                val y1 = event.y
                //得到字母对应的索引
                val index1 = (y1 / cellHeight).toInt()
                if (lastIndex != index1) {
                    //说明当前触摸字母和上一个不是同一个字母
                    LogUtil.e(tag, indexArr[index1])
                    //                    对index做安全性的检查
                    if (index1 < indexArr.size) {
                        if (listener != null) {
                            listener!!.onTouchLetter(indexArr[index1], index1)
                        }
                    }
                }
                lastIndex = index1
            }
            MotionEvent.ACTION_MOVE -> {
                val y = event.y
                //得到字母对应的索引
                val index = (y / cellHeight).toInt()
                if (lastIndex != index) {
                    //说明当前触摸字母和上一个不是同一个字母
                    LogUtil.e(tag, indexArr[index])
                    //对index做安全性的检查
                    if (index < indexArr.size) {
                        if (listener != null) {
                            listener!!.onTouchLetter(indexArr[index], index)
                        }
                    }
                }
                lastIndex = index
            }
            MotionEvent.ACTION_UP -> {
                setBackgroundColor(transparent)
                //重置lastIndex
                lastIndex = -1
            }
            else -> {
            }
        }
        invalidate()
        return true
    }

    private fun getTextHeight(text: String): Int {
        //获取文本的高度
        val bounds = Rect()
        paint!!.getTextBounds(text, 0, text.length, bounds)
        return bounds.height()
    }

    fun setOnTouchLetterListener(listener: OnTouchLetterListener?) {
        this.listener = listener
    }

    interface OnTouchLetterListener {
        fun onTouchLetter(letter: String?, index: Int)
    }


}