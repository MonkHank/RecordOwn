package com.monk.activity.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-06-05
 */
public class BaseView<T extends BaseView<T>> extends View {
    protected final String tag=BaseView.class.getSimpleName();
    protected  String simpleName;

    public BaseView(Context context) {
        this(context,null);
    }

    public BaseView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        BaseView<T> mView = this;
        simpleName= mView.getClass().getSimpleName()+"：";
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtil.i(tag,simpleName+"onFinishInflate");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtil.i(tag,simpleName+"onAttachedToWindow");
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        LogUtil.i(tag,simpleName+"onWindowVisibilityChanged");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(tag,simpleName+"onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.i(tag,simpleName+"onSizeChanged");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.i(tag,simpleName+"onLayout");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        LogUtil.i(tag,simpleName+"onWindowFocusChanged");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtil.e(tag,simpleName+"onDetachedFromWindow");
    }
}
