package com.peoplesafe.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.monk.aidldemo.R;


/**
 * @author MonkeyHank
 * @date 2017-10-25 09:10
 */
public class PercentCircle extends View {
    private TextPaint mTextPaint;
    private Paint mBackgroundPaint;
    private Paint mRingPaint;

    private int mCircleX,mCircleY;

    private RectF mArcRectF;
    private float mStartSweepValue;

    private float mTargetPercent;

    private final int mDefaultRadius = 60;
    private int mRadius;

    private final int mDefaultBackgroundColor = 0xffafb4db;
    private final int mBackgroundColor;

    private final int mDefaultRingColor = 0xff6950a1;
    private final int mRingColor;

    private int mTextSize;


    private final int mDefaultTextColor = 0xffffffff;
    private final int mTextColor;
    private final float density;
    private StaticLayout staticLayout;

    public PercentCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        density =context.getResources().getDisplayMetrics().density;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PercentCircle);

        mRadius = typedArray.getInt(R.styleable.PercentCircle_radius_integer, mDefaultRadius);
        mBackgroundColor = typedArray.getColor(R.styleable.PercentCircle_background_color, mDefaultBackgroundColor);

        mTextColor = typedArray.getColor(R.styleable.PercentCircle_text_color, mDefaultTextColor);

        mRingColor = typedArray.getColor(R.styleable.PercentCircle_ring_color, mDefaultRingColor);

        // Be sure to call recycle() when done with them
        typedArray.recycle();
        init(context);
    }

    private void init(Context context){
        //圆环开始角度 -90° 正北方向
        mStartSweepValue = -90;

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(mBackgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth((float) (0.025*mRadius));
        //文字大小为半径的一半 mRadius/2
        mTextPaint.setTextSize(14* density);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        //设置外圆环的画笔
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth((float) (0.075*mRadius));

        //获得文字的字号 因为要设置文字在圆的中心位置
        mTextSize = (int) mTextPaint.getTextSize();

        String text="任务完成\n"+ (int) mTargetPercent +"%";
        staticLayout = new StaticLayout(text, mTextPaint, (int) (150*density), Layout.Alignment.ALIGN_NORMAL, 1.2F, 1.0F, true);
    }

    /**
     * 主要是测量wrap_content时候的宽和高，因为宽高一样，只需要测量一次宽即可，高等于宽
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(widthMeasureSpec));
    }

    /**
     * 当wrap_content的时候，view的大小根据半径大小改变，但最大不会超过屏幕
     * @param measureSpec
     */
    private int measure(int measureSpec){
        int result ;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if( specMode == MeasureSpec.EXACTLY){
            // 如果测量模式是MatchParent 或者精确值，则宽为测量的宽
            result = specSize;
        }else {
            result =(int) (mRadius*density + mRingPaint.getStrokeWidth()*density);
            if(specMode == MeasureSpec.AT_MOST){
                //如果测量模式是WrapContent ，则宽为 直径值 与 测量宽中的较小值；
                //否则当直径大于测量宽时，会绘制到屏幕之外；
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //1、如果半径大于圆心的横坐标，需要手动缩小半径的值，否则画到屏幕之外；
        //2、改变了半径，则需要重新设置字体大小；
        //3、改变了半径，则需要重新设置外圆环的宽度
        //4、画背景圆的外接矩形，用来画圆环；
        mCircleX = getMeasuredWidth()/2;
        mCircleY = getMeasuredHeight()/2;
        if(mRadius > mCircleX){
            mRadius = mCircleX;
            mRadius = (int) (mCircleX-0.075*mRadius);
            mTextPaint.setStrokeWidth((float) (0.025*mRadius));
            mTextPaint.setTextSize(mRadius/3);
            mRingPaint.setStrokeWidth((float) (0.075*mRadius));
            mTextSize = (int) mTextPaint.getTextSize();
        }
        mArcRectF = new RectF(mCircleX-mRadius, mCircleY-mRadius, mCircleX+mRadius, mCircleY+mRadius);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    @Override
    protected  void onDraw(Canvas canvas) {
        canvas.drawCircle(mCircleX, mCircleY, mRadius, mBackgroundPaint);

        canvas.save();
        canvas.translate(mCircleX, mCircleY/2+10);
        staticLayout.draw(canvas);
        canvas.restore();

        canvas.drawArc(mArcRectF, mStartSweepValue, (float) ((int)mTargetPercent*3.6), false, mRingPaint);

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

    public  void setTargetPercent(float targetPercent){
        mTargetPercent = targetPercent;
        postInvalidate();
    }

    public void setRadius(int radius) {
        mRadius=radius;
    }
}
