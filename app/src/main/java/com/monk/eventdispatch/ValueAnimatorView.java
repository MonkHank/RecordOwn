package com.monk.eventdispatch;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-06-04
 */
public class ValueAnimatorView extends View {

    private Paint mPaint;
    private int mRadius ;
    private ValueAnimator mAnimator;

    public ValueAnimatorView(Context context) {
        this(context,null);
    }

    public ValueAnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xff94E1F7);

        mAnimator = ValueAnimator.ofInt(0, 100);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRadius= (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.setRepeatCount(2);
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(200, 200);
        canvas.drawCircle(0, 0, mRadius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.e("ValueAnimatorView", "onTouchEvent: ");
                mAnimator.start();//点击开启动画
                break;
            case MotionEvent.ACTION_UP:
        }
        return super.onTouchEvent(event);
    }
}
