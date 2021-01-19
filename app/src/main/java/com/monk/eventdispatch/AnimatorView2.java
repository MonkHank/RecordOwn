package com.monk.eventdispatch;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.monk.activity.base.BaseView;
import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-06-05
 */
public class AnimatorView2 extends BaseView {

    private Paint mPaint;
    private final int mRadius =50;
    private ObjectAnimator mAnimator;
    private RectF rectF;

    public AnimatorView2(Context context) {
        this(context,null);
    }

    public AnimatorView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xff94E1F7);
        rectF = new RectF(0, 50, 100, 0);

        // 常用属性
        // alpha translationX translationY rotation rotationX rotationY scaleX scaleY
        mAnimator = ObjectAnimator.ofFloat(this, "rotationX", 0, 360);
        mAnimator.setDuration(2000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(100, 100);
//        canvas.drawCircle(0, 0, mRadius, mPaint);
        canvas.drawRect(rectF,mPaint);
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
