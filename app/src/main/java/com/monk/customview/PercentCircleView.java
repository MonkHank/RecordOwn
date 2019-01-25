package com.monk.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.monk.aidldemo.R;
import com.monk.utils.LogUtil;

/**
 * @author monk
 * @date 2019-01-24
 */
public class PercentCircleView extends View {
    private final String tag = "PercentCircleView";
    private final int paintId;
    private Paint  circlePaint, backgroundPaint, redPaint, circlePaint3;
    private TextPaint textPaint;
    private final float density;
    private RectF rectF, rectF2, rectF3;

    private float backgroundRadius;
    private float circleX, circleY;
    private final StaticLayout staticLayout;


    public PercentCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PercentCircleView);
        paintId = ta.getInteger(R.styleable.PercentCircleView_paintId, 0);
        ta.recycle();

        density = context.getResources().getDisplayMetrics().density;

        textPaint = new TextPaint();
        /* 样式设置
         * paint.setFakeBoldText(true)：设置是否为粗体文字
         * paint.setUnderlineText(true)：设置下划线
         * paint.setTextSkewX((float) -0.25)：设置字体水平倾斜度，普通斜体字是 -0.25
         * paint.setStrikeThruText(true)：设置带有删除线效果
         * paint.setTextScaleX(2)：设置水平拉伸，高度不会变
         */
        // 设置是否使用抗锯齿功能，如果使用，会导致绘图速度变慢
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(14 * density);
        // 设置文字对齐方式
        textPaint.setTextAlign(Paint.Align.CENTER);
        /* 1、Paint.Style.FILL：填充内部
         * 2、Paint.Style.FILL_AND_STROKE：填充内部和描边
         * 3、Paint.Style.STROKE：仅描边
         */
        textPaint.setStyle(Paint.Style.STROKE);
        // 设置画笔宽度
        textPaint.setStrokeWidth(1*density);

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(1 * density);
        backgroundPaint.setColor(Color.GRAY);


        circlePaint = new Paint();
        circlePaint.setColor(Color.GREEN);
        circlePaint.setStrokeWidth(2 * density);

        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(2 * density);
        redPaint.setStyle(Paint.Style.STROKE);

        circlePaint3 = new Paint();
        circlePaint3.setColor(Color.GREEN);
        circlePaint3.setStrokeWidth(50 * density);
        circlePaint3.setStyle(Paint.Style.FILL);

        // 文字的位置跟width有关系
        String text="one \n No2";
        staticLayout = new StaticLayout(text,textPaint, (int) (100*density), Layout.Alignment.ALIGN_NORMAL, 1.2f, 1, false);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
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
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogUtil.i(tag, "onSizeChanged：" + w + "\t" + h + "\t" + oldw + "\t" + oldh);
        int height = getHeight();
        int measuredHeight = getMeasuredHeight();
        int width = getWidth();
        int measuredWidth = getMeasuredWidth();
        LogUtil.w(tag, "onSizeChanged：" + height + "\t" + measuredHeight + "\t" + width + "\t" + measuredWidth);
        float x = (getWidth() - (getHeight() >> 1)) >> 1;
        float y = getHeight() >> 2;

        // 圆弧的外切圆,left,top,right,bottom边离x、y轴的坐标
        rectF = new RectF(0, 200, 400, 300);

        rectF2 = new RectF(200, 200, 50, 100);

        circleX = width >> 1;
        circleY = height >> 1;
        backgroundRadius = 60 * density;

        rectF3 = new RectF(circleX - backgroundRadius, circleY - backgroundRadius, circleX + backgroundRadius, circleY + backgroundRadius);


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        LogUtil.i(tag, "onLayout：" + changed + "\t" + left + "\t" + top + "\t" + right + "\t" + bottom);
    }

    /**
     * 覆盖而画，和FrameLayout机制类似
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.w(tag, "canvas：" + canvas);
        canvas.drawCircle(circleX, circleY, backgroundRadius, backgroundPaint);
        canvas.drawArc(rectF3, 0, 180, false, redPaint);

//        canvas.drawText(text, circleX , circleY, textPaint);
        canvas.drawArc(rectF, 0, 90, true, circlePaint);
        canvas.drawArc(rectF, 0, 90, false, redPaint);
        canvas.drawRect(rectF, backgroundPaint);

        canvas.drawPoint(400, 100, circlePaint3);

        canvas.drawRect(rectF2, backgroundPaint);
        canvas.drawOval(rectF2, redPaint);

        canvas.save();
        canvas.translate(circleX,circleY-10*density);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.i(tag, "x = " + x + "\t y = " + y);
                break;
            default:
                break;
        }
        return true;
    }
}
