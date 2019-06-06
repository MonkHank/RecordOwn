package com.monk.eventdispatch;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AnticipateOvershootInterpolator;

import com.monk.base.BaseView;

/**
 * @author monkey
 * @date 2019-6-5 11:03:39
 */
public class AnimatorPathView extends BaseView {
    private static final String TAG = "AnimatorView";

    private Paint mPaint;
    private Path mPath;
    private PathMeasure pathMeasure;

    public AnimatorPathView(Context context) {
        this(context, null);
    }

    public AnimatorPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xff94E1F7);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        //测量路径
        mPath = new Path();
        mPath = nStarPath(mPath, 8, 50, 20);//八角形路径
        pathMeasure = new PathMeasure(mPath, false);
    }

    private ValueAnimator createAnimator() {
        ValueAnimator mAnimator = ValueAnimator.ofInt(0, 800);
        mAnimator.setRepeatCount(1);
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.setDuration(3000);
        mAnimator.setInterpolator(new AnticipateOvershootInterpolator());

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = animation.getAnimatedFraction();
                //核心:创建DashPathEffect
                DashPathEffect effect = new DashPathEffect(
                        new float[]{
                                pathMeasure.getLength(),
                                pathMeasure.getLength()},
                        value * pathMeasure.getLength());
                mPaint.setPathEffect(effect);
                invalidate();
            }
        });
        return mAnimator;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(100, 100);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                createAnimator().start();
                break;
            case MotionEvent.ACTION_UP:
        }
        return super.onTouchEvent(event);
    }

    /**
     * n角星路径
     *
     * @param num 几角星
     * @param R   外接圆半径
     * @param r   内接圆半径
     * @return n角星路径
     */
    public static Path nStarPath(Path path, int num, float R, float r) {
        float perDeg = 360 / num;
        float degA = perDeg / 2 / 2;
        float degB = 360 / (num - 1) / 2 - degA / 2 + degA;
        path.moveTo((float) (Math.cos(rad(degA)) * R), (float) (-Math.sin(rad(degA)) * R));
        for (int i = 0; i < num; i++) {
            path.lineTo(
                    (float) (Math.cos(rad(degA + perDeg * i)) * R),
                    (float) (-Math.sin(rad(degA + perDeg * i)) * R));
            path.lineTo(
                    (float) (Math.cos(rad(degB + perDeg * i)) * r),
                    (float) (-Math.sin(rad(degB + perDeg * i)) * r));
        }
        path.close();
        return path;
    }

    /**
     * 角度制化为弧度制
     *
     * @param deg 角度
     * @return 弧度
     */
    public static float rad(float deg) {
        return (float) (deg * Math.PI / 180);
    }
}
