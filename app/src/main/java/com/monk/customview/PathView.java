package com.monk.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * path 的一些to方法
 * https://blog.csdn.net/a394268045/article/details/70792619
 * @author monk
 * @date 2020/1/14
 */
public class PathView extends View {
    private Paint mPaint;
    private Path mPath;

    public PathView(Context context) {
        super(context);
        initView();
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);// 如果不设置，默认是填充

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.quadTo(300, 100, 600, 500);// 贝塞尔曲线
        canvas.drawPath(mPath, mPaint);
    }
}
