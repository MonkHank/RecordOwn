package com.monk.moduleviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.monk.commonutils.LogUtils;
import com.monk.moduleviews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 曲线图、折线图
 *  https://yq.aliyun.com/articles/638336
 * @author monk
 * @date 2020/1/13
 */
public class LineChartView extends View {
    private final Context mContext;

    private Paint mAxisPaint; //绘制坐标轴的画笔
    private Paint mPaint; //绘制曲线的画笔
    private Paint mXAxisLinePaint;  //绘制X轴上方的画笔
    private Paint mPaintText;

    private int startX; //向上的曲线图的绘制起点(px)
    private int startY;

    private int downStartX; //向下的曲线图的绘制起点(px)
    private int downStartY;

    private float YAxisUpUnitValue;   //上方Y轴每单位刻度所占的像素值
    private float YAxisDownUnitValue; //下方Y轴每单位刻度所占的像素值

    private Point[] mPoints;//根据具体传入的数据，在坐标轴上绘制点

    private List<Integer> mDatas = new ArrayList<>(); //传入的数据，决定绘制的纵坐标值
    private List<Integer> mYAxisList = new ArrayList<>();//Y轴刻度集合
    private List<String> mXAxisList = new ArrayList<>(); //X轴刻度集合

    private int mXAxisMaxValue; //X轴的绘制距离
    private int mYAxisMaxValue;  //Y轴的绘制距离

    private final int yAxisSpace = 120; //Y轴刻度间距(px)
    private final int xAxisSpace = 200;  //X轴刻度间距(px)

    private final int mKeduWidth = 20; //Y轴刻度线宽度

    private final float keduTextSize = 20;

    private final int textPadinng = 10; //刻度值距离坐标的padding距离

    private int yIncreaseValue;  //Y轴递增的实际值
    private final boolean isCurve = true;  //true：绘制曲线 false：折线
    private int mMaxTextWidth;
    private int mMaxTextHeight;

    private Rect rect;
    private Point[] p3s;
    private Point[] p4s;
    private Path[] paths;

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initData();
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // UNSPECIFIED：不对View大小做限制，如：ListView，ScrollView
        // EXACTLY：确切的大小，如：100dp或者march_parent
        // AT_MOST：大小不可超过某数值，如：wrap_content
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (mYAxisList.size() - 1) * yAxisSpace + mMaxTextHeight * 2 + textPadinng * 2;
        }
        if (widthMode == MeasureSpec.AT_MOST) {

            widthSize = startX + (mDatas.size() - 1) * xAxisSpace + mMaxTextWidth;
        }
        //保存测量结果
        setMeasuredDimension(widthSize, heightSize);
    }

    private void initView() {
        //初始化画笔
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.orange_FF6600));
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        //绘制X,Y轴坐标的画笔
        mAxisPaint = new Paint();
        mAxisPaint.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        mAxisPaint.setStrokeWidth(5);
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStyle(Paint.Style.STROKE);

        //绘制坐标轴上方的横线的画笔
        mXAxisLinePaint = new Paint();
        mXAxisLinePaint.setColor(ContextCompat.getColor(mContext, R.color.black_overlay));
        mXAxisLinePaint.setStrokeWidth(1);
        mXAxisLinePaint.setAntiAlias(true);
        mXAxisLinePaint.setStyle(Paint.Style.STROKE);

        //绘制刻度值文字的画笔
        mPaintText = new Paint();
        mPaintText.setTextSize(keduTextSize);
        mPaintText.setColor(ContextCompat.getColor(mContext, R.color.green));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(1);

        Rect mYMaxTextRect = new Rect();
        Rect mXMaxTextRect = new Rect();
        mPaintText.getTextBounds(Integer.toString(mYAxisList.get(mYAxisList.size() - 1)), 0, Integer.toString(mYAxisList.get(mYAxisList.size() - 1)).length(), mYMaxTextRect);
        mPaintText.getTextBounds(mXAxisList.get(mXAxisList.size() - 1), 0, mXAxisList.get(mXAxisList.size() - 1).length(), mXMaxTextRect);

        //绘制的刻度文字的最大值所占的宽高
        mMaxTextWidth = mYMaxTextRect.width() > mXMaxTextRect.width() ? mYMaxTextRect.width() : mXMaxTextRect.width();
        mMaxTextHeight = mYMaxTextRect.height() > mXMaxTextRect.height() ? mYMaxTextRect.height() : mXMaxTextRect.height();

        //指定绘制的起始位置
        startX = mMaxTextWidth + textPadinng + mKeduWidth;

        //坐标原点Y的位置（+1的原因：X轴画笔的宽度为2 ; +DP2PX.dip2px(mContext, 5)原因：为刻度文字所占的超出的高度 ）——>解决曲线画到最大刻度值时，显示高度不够，曲线显示扁扁的问题
        startY = yAxisSpace * (mYAxisList.size() - 1) + mMaxTextHeight;

        if (mYAxisList.size() >= 2) {
            yIncreaseValue = mYAxisList.get(1) - mYAxisList.get(0);
        }

        //X轴绘制距离
        mXAxisMaxValue = (mDatas.size() - 1) * xAxisSpace;
        //Y轴绘制距离
        mYAxisMaxValue = (mYAxisList.size() - 1) * yAxisSpace;

        //坐标起始点Y轴高度=(startY+mKeduWidth)  下方文字所占高度= DP2PX.dip2px(mContext, keduTextSize)
        int viewHeight = startY + 2 * mKeduWidth + dp2px(keduTextSize);
        //viewHeight=121
        LogUtils.e("TAG", "viewHeight = " + viewHeight);

        mPoints = initPoint();

        rect = new Rect();

        p3s = new Point[mDatas.size() - 1];
        p4s = new Point[mDatas.size() - 1];
        paths = new Path[mDatas.size() - 1];
        for (int i = 0; i < mDatas.size() - 1; i++) {
            p3s[i]= new Point();
            p4s[i] = new Point();
            paths[i] = new Path();
        }
    }

    private   int dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据传入的数据，确定绘制的点
     *
     * @return
     */
    private Point[] initPoint() {
        Point[] points = new Point[mDatas.size()];
        for (int i = 0; i < mDatas.size(); i++) {
            Integer ybean = mDatas.get(i);
            int drawHeight = (int) (startY * 1.0 - (ybean * yAxisSpace * 1.0 / yIncreaseValue));
            int startx = startX + xAxisSpace * i;
            points[i] = new Point(startx, drawHeight);
        }
        LogUtils.e("TAG", "startX = " + startX + "---start Y = " + startY);
        return points;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mYAxisList.size(); i++) {
            //Y轴方向递增的高度
            int yAxisHeight = startY - yAxisSpace * i;
            //绘制X轴和上方横线
            canvas.drawLine(startX - mKeduWidth, yAxisHeight, startX + (mDatas.size() - 1) * xAxisSpace, yAxisHeight, mXAxisLinePaint);
            //绘制左边Y轴刻度线
//                canvas.drawLine(startX, yAxisHeight, startX - mKeduWidth, yAxisHeight, mAxisPaint);
            //绘制文字时,Y轴方向递增的高度
            int yTextHeight = startY - yAxisSpace * i;
            //绘制Y轴刻度旁边的刻度文字值,10为刻度线与文字的间距
            mPaintText.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(mYAxisList.get(i) + "", (startX - mKeduWidth) - textPadinng, yTextHeight, mPaintText);
        }
        //绘制Y轴
        canvas.drawLine(startX, startY, startX, startY - mYAxisMaxValue, mAxisPaint);

        //绘制X轴下面显示的文字
        for (int i = 0; i < mXAxisList.size(); i++) {
            int xTextWidth = startX + xAxisSpace * i - mKeduWidth;

            //设置从起点位置的左边对齐绘制文字
            mPaintText.setTextAlign(Paint.Align.LEFT);

            mPaintText.getTextBounds(mXAxisList.get(i), 0, mXAxisList.get(i).length(), rect);
            canvas.drawText(mXAxisList.get(i), startX - rect.width() / 2 + xAxisSpace * i, startY + rect.height() + textPadinng, mPaintText);
        }
        //连接所有的数据点,画曲线

        if (isCurve) {
            //画曲线
            drawScrollLine(canvas);
        } else {
            //画折线
            drawLine(canvas);
        }
    }

    /**
     * 绘制曲线-曲线图
     *
     * @param canvas
     */
    private void drawScrollLine(Canvas canvas) {
        Point startp;
        Point endp;
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            int wt = (startp.x + endp.x) / 2;
            p3s[i].y = startp.y;
            p3s[i].x = wt;
            p4s[i].y = endp.y;
            p4s[i].x = wt;
            paths[i].moveTo(startp.x, startp.y);
            paths[i].cubicTo(p3s[i].x, p3s[i].y, p4s[i].x, p4s[i].y, endp.x, endp.y);
            canvas.drawPath(paths[i], mPaint);
        }
    }

    /**
     * 绘制直线-折线图
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        Point startp;
        Point endp;
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
        }
    }

    private void initData() {
        //外界传入的数据，即为绘制曲线的每个点
        mDatas.add(0);
        mDatas.add(10);
        mDatas.add(5);
        mDatas.add(20);
        mDatas.add(15);

        int[] mYAxisData = new int[]{0, 10, 20, 30, 40};
        for (int mYAxisDatum : mYAxisData) {
            mYAxisList.add(mYAxisDatum);
        }

        //X轴数据
        mXAxisList.add("01月");
        mXAxisList.add("02月");
        mXAxisList.add("03月");
        mXAxisList.add("04月");
        mXAxisList.add("05月");
    }

    /**
     * 传入数据，重新绘制图表
     *
     * @param datas
     * @param yAxisData
     */
    public void updateData(List<Integer> datas, List<String> xAxisData, List<Integer> yAxisData) {
        this.mDatas = datas;
        this.mXAxisList = xAxisData;
        this.mYAxisList = yAxisData;
        initView();
        postInvalidate();
    }
}
