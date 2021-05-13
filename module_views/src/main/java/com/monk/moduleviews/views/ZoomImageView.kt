package com.monk.moduleviews.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.sqrt

class ZoomImageView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context!!, attrs) {
    private val matrixs = Matrix()
    private val savedMatrix = Matrix()
    private var mode = NONE

    // 第一个按下的手指的点
    private val startPoint = PointF()

    // 两个按下的手指的触摸点的中点
    private var midPoint = PointF()

    // 初始的两个手指按下的触摸点的距离
    private var oriDis = 1f
    private var count = 0

    companion object {
        private const val NONE = 0
        private const val DRAG = 1
        private const val ZOOM = 2
        private const val MAX_SCALE = 3f
        private const val mCurScale = 1.0f
        private var mStartScale = 0f
    }

    override fun onDraw(canvas: Canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas)
        if (count == 0) {
            val values = FloatArray(9)
            imageMatrix.getValues(values)
            mStartScale = values[Matrix.MSCALE_X]
            Log.e("", "s=$mStartScale")
            count++
        }
    }

    // return scale!=1.0f;
    private val isZoomChanged: Float
        get() {
            val values = FloatArray(9)
            imageMatrix.getValues(values)
            val scale = values[Matrix.MSCALE_X]
            return if (scale != mStartScale) mStartScale / scale else 2.0f
            // return scale!=1.0f;
        }

    override fun setImageBitmap(bm: Bitmap) {
        // TODO Auto-generated method stub
        super.setImageBitmap(bm)
    }

    private var startTime: Long = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 进行与操作是为了判断多点触摸
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                // 第一个手指按下事件
                scaleType = ScaleType.MATRIX
                val interval = event.eventTime - startTime
                Log.e("", "now-st=" + interval + "st=" + startTime)
                if (interval < 300) {
                    Log.e("", "double click")
                    matrixs.set(imageMatrix)
                    val scale = isZoomChanged
                    Log.e("", "scale=$scale")
                    matrixs.postScale(scale, scale, (width / 2).toFloat(), (height / 2).toFloat())
                    imageMatrix = matrixs
                }else{
                    startTime = event.eventTime
                    Log.e("", "down")
                    Log.e("", "width=" + drawable.bounds.width())
                    matrixs.set(imageMatrix)
                    savedMatrix.set(matrixs)
                    startPoint[event.x] = event.y
                    mode = DRAG
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                // 第二个手指按下事件
                Log.e("", "double down")
                oriDis = distance(event)
                if (oriDis > 10f) {
                    savedMatrix.set(matrixs)
                    midPoint = middle(event)
                    mode = ZOOM
                }
            }
            MotionEvent.ACTION_UP ->                 // 手指放开事件
                mode = NONE
            MotionEvent.ACTION_POINTER_UP ->                 // 手指放开事件
                mode = NONE
            MotionEvent.ACTION_MOVE ->
                // 手指滑动事件
                if (mode == DRAG) {
                    // 是一个手指拖动
                    matrixs.set(savedMatrix)
                    Log.e("mmm", "event.getX() - startPoint.x＝" + event.x + "-" + startPoint.x)
                    Log.e("mmm", matrixs.toString())
                    matrixs.postTranslate(event.x - startPoint.x, event.y - startPoint.y)
                    Log.e("mmm", "x=" + x + "y=" + y)
                } else if (mode == ZOOM) {
                    // 两个手指滑动
                    val newDist = distance(event)
                    if (newDist > 10f) {
                        matrixs.set(savedMatrix)
                        var scale = newDist / oriDis
                        val values = FloatArray(9)
                        matrixs.getValues(values)
                        scale = checkMaxScale(scale, values)
                    }
                }
        }

        // 设置ImageView的Matrix
        this.imageMatrix = matrixs
        return true
    }

    /**
     * 检验scale，使图像缩放后不会超出最大倍数
     *
     * @param scale
     * @param values
     * @return
     */
    private fun checkMaxScale(scale: Float, values: FloatArray): Float {
        var scale = scale
        if (scale * values[Matrix.MSCALE_X] > MAX_SCALE) scale = MAX_SCALE / values[Matrix.MSCALE_X] else if (scale * values[Matrix.MSCALE_X] < mStartScale) scale = mStartScale / values[Matrix.MSCALE_X]
        matrixs.postScale(scale, scale, (width / 2).toFloat(), (height / 2).toFloat())
        return scale
    }

    // 计算两个触摸点之间的距离
    private fun distance(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }

    // 计算两个触摸点的中点
    private fun middle(event: MotionEvent): PointF {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        return PointF(x / 2, y / 2)
    }

}