package com.monk.moduleviews.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;


public class ZoomImageView extends AppCompatImageView {
    private final Matrix matrix = new Matrix();
    private final Matrix savedMatrix = new Matrix();

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private static final float MAX_SCALE = 3;
    private static final float mCurScale = 1.0f;
    private static float mStartScale;
    // 第一个按下的手指的点
    private final PointF startPoint = new PointF();
    // 两个按下的手指的触摸点的中点
    private PointF midPoint = new PointF();
    // 初始的两个手指按下的触摸点的距离
    private float oriDis = 1f;

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // mGestureDetector=new GestureDetector(context, new GestureListener());

    }

    private int count = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (count == 0) {
            float[] values = new float[9];
            getImageMatrix().getValues(values);

            mStartScale = values[Matrix.MSCALE_X];
            Log.e("", "s=" + mStartScale);
            count++;
        }

    }

    private float isZoomChanged() {
        float[] values = new float[9];
        getImageMatrix().getValues(values);

        float scale = values[Matrix.MSCALE_X];
        if (scale != mStartScale)
            return mStartScale / scale;
        else
            return 2.0f;
        // return scale!=1.0f;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        // TODO Auto-generated method stub
        super.setImageBitmap(bm);

    }

    private long startTime = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 进行与操作是为了判断多点触摸
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // 第一个手指按下事件
                setScaleType(ScaleType.MATRIX);
                long interval = event.getEventTime() - startTime;
                Log.e("", "now-st=" + interval + "st=" + startTime);

                if (interval < 300) {
                    Log.e("", "double click");
                    matrix.set(getImageMatrix());

                    float scale = isZoomChanged();
                    Log.e("", "scale=" + scale);
                    matrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
                    setImageMatrix(matrix);
                    break;
                }

                startTime = event.getEventTime();
                Log.e("", "down");
                Log.e("", "width=" + getDrawable().getBounds().width());
                matrix.set(getImageMatrix());
                savedMatrix.set(matrix);
                startPoint.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // 第二个手指按下事件

                Log.e("", "double down");
                oriDis = distance(event);
                if (oriDis > 10f) {
                    savedMatrix.set(matrix);
                    midPoint = middle(event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
                // 手指放开事件
                mode = NONE;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                // 手指放开事件
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:

                // 手指滑动事件
                if (mode == DRAG) {
                    // 是一个手指拖动

                    matrix.set(savedMatrix);
                    Log.e("mmm", "event.getX() - startPoint.x＝" + event.getX()
                            + "-" + startPoint.x);
                    Log.e("mmm", matrix.toString());
                    matrix.postTranslate(event.getX() - startPoint.x, event.getY()
                            - startPoint.y);
                    Log.e("mmm", "x=" + getX() + "y=" + getY());
                } else if (mode == ZOOM) {
                    // 两个手指滑动
                    float newDist = distance(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oriDis;
                        float[] values = new float[9];
                        matrix.getValues(values);
                        scale = checkMaxScale(scale, values);
                    }
                }
                break;
        }

        // 设置ImageView的Matrix
        this.setImageMatrix(matrix);
        return true;
    }

    /**
     * 检验scale，使图像缩放后不会超出最大倍数
     *
     * @param scale
     * @param values
     * @return
     */
    private float checkMaxScale(float scale, float[] values) {
        if (scale * values[Matrix.MSCALE_X] > MAX_SCALE)
            scale = MAX_SCALE / values[Matrix.MSCALE_X];
        else if (scale * values[Matrix.MSCALE_X] < mStartScale)
            scale = mStartScale / values[Matrix.MSCALE_X];
        matrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        return scale;
    }

    // 计算两个触摸点之间的距离
    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 计算两个触摸点的中点
    private PointF middle(MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new PointF(x / 2, y / 2);
    }

}
