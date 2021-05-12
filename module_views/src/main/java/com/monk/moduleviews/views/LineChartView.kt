package com.monk.moduleviews.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.monk.commonutils.LogUtils
import com.monk.moduleviews.R
import java.util.*

/**
 * 曲线图、折线图
 * https://yq.aliyun.com/articles/638336
 * @author monk
 * @date 2020/1/13
 */
class LineChartView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(mContext, attrs, defStyleAttr) {
    private var mAxisPaint //绘制坐标轴的画笔
            : Paint? = null
    private var mPaint //绘制曲线的画笔
            : Paint? = null
    private var mXAxisLinePaint //绘制X轴上方的画笔
            : Paint? = null
    private var mPaintText: Paint? = null
    private var startX //向上的曲线图的绘制起点(px)
            = 0
    private var startY = 0
    private val downStartX //向下的曲线图的绘制起点(px)
            = 0
    private val downStartY = 0
    private val YAxisUpUnitValue //上方Y轴每单位刻度所占的像素值
            = 0f
    private val YAxisDownUnitValue //下方Y轴每单位刻度所占的像素值
            = 0f
    private lateinit var mPoints //根据具体传入的数据，在坐标轴上绘制点
            : Array<Point?>
    private var mDatas: MutableList<Int> = ArrayList() //传入的数据，决定绘制的纵坐标值
    private var mYAxisList: MutableList<Int> = ArrayList() //Y轴刻度集合
    private var mXAxisList: MutableList<String> = ArrayList() //X轴刻度集合
    private var mXAxisMaxValue //X轴的绘制距离
            = 0
    private var mYAxisMaxValue //Y轴的绘制距离
            = 0
    private val yAxisSpace = 120 //Y轴刻度间距(px)
    private val xAxisSpace = 200 //X轴刻度间距(px)
    private val mKeduWidth = 20 //Y轴刻度线宽度
    private val keduTextSize = 20f
    private val textPadinng = 10 //刻度值距离坐标的padding距离
    private var yIncreaseValue //Y轴递增的实际值
            = 0
    private val isCurve = true //true：绘制曲线 false：折线
    private var mMaxTextWidth = 0
    private var mMaxTextHeight = 0
    private var rect: Rect? = null
    private lateinit var p3s: Array<Point?>
    private lateinit var p4s: Array<Point?>
    private lateinit var paths: Array<Path?>

    init {
        initData()
        initView()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        // UNSPECIFIED：不对View大小做限制，如：ListView，ScrollView
        // EXACTLY：确切的大小，如：100dp或者march_parent
        // AT_MOST：大小不可超过某数值，如：wrap_content
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (mYAxisList.size - 1) * yAxisSpace + mMaxTextHeight * 2 + textPadinng * 2
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = startX + (mDatas.size - 1) * xAxisSpace + mMaxTextWidth
        }
        //保存测量结果
        setMeasuredDimension(widthSize, heightSize)
    }

    private fun initView() {
        //初始化画笔
        mPaint = Paint()
        mPaint!!.color = ContextCompat.getColor(mContext, R.color.orange_FF6600)
        mPaint!!.strokeWidth = 2f
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.STROKE

        //绘制X,Y轴坐标的画笔
        mAxisPaint = Paint()
        mAxisPaint!!.color = ContextCompat.getColor(mContext, R.color.colorAccent)
        mAxisPaint!!.strokeWidth = 5f
        mAxisPaint!!.isAntiAlias = true
        mAxisPaint!!.style = Paint.Style.STROKE

        //绘制坐标轴上方的横线的画笔
        mXAxisLinePaint = Paint()
        mXAxisLinePaint!!.color = ContextCompat.getColor(mContext, R.color.black_overlay)
        mXAxisLinePaint!!.strokeWidth = 1f
        mXAxisLinePaint!!.isAntiAlias = true
        mXAxisLinePaint!!.style = Paint.Style.STROKE

        //绘制刻度值文字的画笔
        mPaintText = Paint()
        mPaintText!!.textSize = keduTextSize
        mPaintText!!.color = ContextCompat.getColor(mContext, R.color.green)
        mPaintText!!.isAntiAlias = true
        mPaintText!!.strokeWidth = 1f
        val mYMaxTextRect = Rect()
        val mXMaxTextRect = Rect()
        mPaintText!!.getTextBounds(Integer.toString(mYAxisList[mYAxisList.size - 1]), 0, Integer.toString(mYAxisList[mYAxisList.size - 1]).length, mYMaxTextRect)
        mPaintText!!.getTextBounds(mXAxisList[mXAxisList.size - 1], 0, mXAxisList[mXAxisList.size - 1].length, mXMaxTextRect)

        //绘制的刻度文字的最大值所占的宽高
        mMaxTextWidth = if (mYMaxTextRect.width() > mXMaxTextRect.width()) mYMaxTextRect.width() else mXMaxTextRect.width()
        mMaxTextHeight = if (mYMaxTextRect.height() > mXMaxTextRect.height()) mYMaxTextRect.height() else mXMaxTextRect.height()

        //指定绘制的起始位置
        startX = mMaxTextWidth + textPadinng + mKeduWidth

        //坐标原点Y的位置（+1的原因：X轴画笔的宽度为2 ; +DP2PX.dip2px(mContext, 5)原因：为刻度文字所占的超出的高度 ）——>解决曲线画到最大刻度值时，显示高度不够，曲线显示扁扁的问题
        startY = yAxisSpace * (mYAxisList.size - 1) + mMaxTextHeight
        if (mYAxisList.size >= 2) {
            yIncreaseValue = mYAxisList[1] - mYAxisList[0]
        }

        //X轴绘制距离
        mXAxisMaxValue = (mDatas.size - 1) * xAxisSpace
        //Y轴绘制距离
        mYAxisMaxValue = (mYAxisList.size - 1) * yAxisSpace

        //坐标起始点Y轴高度=(startY+mKeduWidth)  下方文字所占高度= DP2PX.dip2px(mContext, keduTextSize)
        val viewHeight = startY + 2 * mKeduWidth + dp2px(keduTextSize)
        //viewHeight=121
        LogUtils.e("TAG", "viewHeight = $viewHeight")
        mPoints = initPoint()
        rect = Rect()
        p3s = arrayOfNulls(mDatas.size - 1)     //   p3s = new Point[mDatas.size() - 1];
        p4s = arrayOfNulls(mDatas.size - 1)     //  p4s = new Point[mDatas.size() - 1];
        paths = arrayOfNulls(mDatas.size - 1)   // paths = new Path[mDatas.size() - 1];
        for (i in 0 until mDatas.size - 1) {  // for (int i = 0; i < mDatas.size() - 1; i++)
            p3s[i] = Point()
            p4s[i] = Point()
            paths[i] = Path()
        }
    }

    private fun dp2px(dpValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据传入的数据，确定绘制的点
     *
     * @return
     */
    private fun initPoint(): Array<Point?> {
        val points = arrayOfNulls<Point>(mDatas.size)
        for (i in mDatas.indices) {
            val ybean = mDatas[i]
            val drawHeight = (startY * 1.0 - ybean * yAxisSpace * 1.0 / yIncreaseValue).toInt()
            val startx = startX + xAxisSpace * i
            points[i] = Point(startx, drawHeight)
        }
        LogUtils.e("TAG", "startX = $startX---start Y = $startY")
        return points
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in mYAxisList.indices) {
            //Y轴方向递增的高度
            val yAxisHeight = startY - yAxisSpace * i
            //绘制X轴和上方横线
            canvas.drawLine((startX - mKeduWidth).toFloat(), yAxisHeight.toFloat(), (startX + (mDatas.size - 1) * xAxisSpace).toFloat(), yAxisHeight.toFloat(), mXAxisLinePaint!!)
            //绘制左边Y轴刻度线
//                canvas.drawLine(startX, yAxisHeight, startX - mKeduWidth, yAxisHeight, mAxisPaint);
            //绘制文字时,Y轴方向递增的高度
            val yTextHeight = startY - yAxisSpace * i
            //绘制Y轴刻度旁边的刻度文字值,10为刻度线与文字的间距
            mPaintText!!.textAlign = Paint.Align.RIGHT
            canvas.drawText(mYAxisList[i].toString() + "", (startX - mKeduWidth - textPadinng).toFloat(), yTextHeight.toFloat(), mPaintText!!)
        }
        //绘制Y轴
        canvas.drawLine(startX.toFloat(), startY.toFloat(), startX.toFloat(), (startY - mYAxisMaxValue).toFloat(), mAxisPaint!!)

        //绘制X轴下面显示的文字
        for (i in mXAxisList.indices) {
            val xTextWidth = startX + xAxisSpace * i - mKeduWidth

            //设置从起点位置的左边对齐绘制文字
            mPaintText!!.textAlign = Paint.Align.LEFT
            mPaintText!!.getTextBounds(mXAxisList[i], 0, mXAxisList[i].length, rect)
            canvas.drawText(mXAxisList[i], (startX - rect!!.width() / 2 + xAxisSpace * i).toFloat(), (startY + rect!!.height() + textPadinng).toFloat(), mPaintText!!)
        }
        //连接所有的数据点,画曲线
        if (isCurve) {
            //画曲线
            drawScrollLine(canvas)
        } else {
            //画折线
            drawLine(canvas)
        }
    }

    /**
     * 绘制曲线-曲线图
     *
     * @param canvas
     */
    private fun drawScrollLine(canvas: Canvas) {
        var startp: Point?
        var endp: Point?
        for (i in 0 until mPoints.size - 1) {
            startp = mPoints[i]
            endp = mPoints[i + 1]
            val wt = (startp!!.x + endp!!.x) / 2
            p3s[i]!!.y = startp.y
            p3s[i]!!.x = wt
            p4s[i]!!.y = endp.y
            p4s[i]!!.x = wt
            paths[i]!!.moveTo(startp.x.toFloat(), startp.y.toFloat())
            paths[i]!!.cubicTo(p3s[i]!!.x.toFloat(), p3s[i]!!.y.toFloat(), p4s[i]!!.x.toFloat(), p4s[i]!!.y.toFloat(), endp.x.toFloat(), endp.y.toFloat())
            canvas.drawPath(paths[i]!!, mPaint!!)
        }
    }

    /**
     * 绘制直线-折线图
     *
     * @param canvas
     */
    private fun drawLine(canvas: Canvas) {
        var startp: Point?
        var endp: Point?
        for (i in 0 until mPoints.size - 1) {
            startp = mPoints[i]
            endp = mPoints[i + 1]
            canvas.drawLine(startp!!.x.toFloat(), startp.y.toFloat(), endp!!.x.toFloat(), endp.y.toFloat(), mPaint!!)
        }
    }

    private fun initData() {
        //外界传入的数据，即为绘制曲线的每个点
        mDatas.add(0)
        mDatas.add(10)
        mDatas.add(5)
        mDatas.add(20)
        mDatas.add(15)
        val mYAxisData = intArrayOf(0, 10, 20, 30, 40)
        for (mYAxisDatum in mYAxisData) {
            mYAxisList.add(mYAxisDatum)
        }

        //X轴数据
        mXAxisList.add("01月")
        mXAxisList.add("02月")
        mXAxisList.add("03月")
        mXAxisList.add("04月")
        mXAxisList.add("05月")
    }

    /**
     * 传入数据，重新绘制图表
     *
     * @param datas
     * @param yAxisData
     */
    fun updateData(datas: MutableList<Int>, xAxisData: MutableList<String>, yAxisData: MutableList<Int>) {
        mDatas = datas
        mXAxisList = xAxisData
        mYAxisList = yAxisData
        initView()
        postInvalidate()
    }

}