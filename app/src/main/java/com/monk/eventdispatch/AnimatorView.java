package com.monk.eventdispatch;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.monk.base.BaseView;
import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-06-04
 */
public class AnimatorView extends BaseView<AnimatorView> {

    private Paint mPaint;
    private int mRadius =50;
    private ValueAnimator mAnimator;

    public AnimatorView(Context context) {
        this(context,null);
    }

    public AnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xff94E1F7);

//        mAnimator = ValueAnimator.ofInt(0, 100);
//        mAnimator = ValueAnimator.ofArgb(0xff94E1F7, 0xffF35519);
        BallEvaluator ballEvaluator = new BallEvaluator();
        Ball ball = new Ball(50, 0xff94E1F7);
        Ball ballEnd = new Ball(100, 0xffF35519);
        mAnimator = ValueAnimator.ofObject(ballEvaluator, ball, ballEnd);

        // LinearInterpolator BounceInterpolator  AnticipateOvershootInterpolator
        // OvershootInterpolator
        mAnimator.setInterpolator(new SinInter());

        mAnimator.setDuration(5000);

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                LogUtil.v(tag,simpleName+"value="+ animatedValue);

                Ball ball = (Ball) animatedValue;
                mRadius=ball.r;
                mPaint.setColor(ball.color);

//                mPaint.setColor((Integer) animatedValue);
//                mRadius= (int) animatedValue;
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(100, 100);
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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 不取消，则 animator在view detached后任然在执行
        mAnimator.cancel();
    }
}
