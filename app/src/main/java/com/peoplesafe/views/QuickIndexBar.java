package com.peoplesafe.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2017/6/2.
 */
public class QuickIndexBar extends View {
    private final String tag = "QuickIndexBar";

    private final float density;
    private String[] indexArr = { "#","A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };
    private Paint paint;
    private float cellHeight;
    private int width;

    /*** 记录上一次按下的字母索引*/
    private int lastIndex = -1;

    private int mid_transparent=0x11000000;
    private int transparent=0x00000000;

    private OnTouchLetterListener listener;


    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        density = context.getResources().getDisplayMetrics().density;
    }


    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(10*density);
        // 设置文本的起点是文字边框底边的中心
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL);
    }

    /**
     * onMeasure方法之后执行，onLayout方法之前执行
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        cellHeight = getMeasuredHeight() *1.0f/indexArr.length;
        LogUtil.v(QuickIndexBar.this,"自定义控件宽:"+width+"像素");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < indexArr.length; i++) {
            float x = width >> 1;
            float y = cellHeight/2 + (getTextHeight(indexArr[i]) >> 1) + i*cellHeight;
            if (lastIndex==i) {
                paint.setColor(Color.BLUE);
                canvas.drawCircle(x, y- (getTextHeight(indexArr[i]) >> 1), (18 >> 1) +4, paint);
                paint.setColor(Color.WHITE);
            }else {
                paint.setColor(Color.BLACK);
            }
            canvas.drawText(indexArr[i], x, y, paint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(mid_transparent);
                float y1 = event.getY();
                //得到字母对应的索引
                int index1 = (int) (y1/cellHeight);
                if(lastIndex!=index1){
                    //说明当前触摸字母和上一个不是同一个字母
              LogUtil.e(tag, indexArr[index1]);
//                    对index做安全性的检查
                    if(index1 < indexArr.length){
                        if(listener!=null){
                            listener.onTouchLetter(indexArr[index1],index1);
                        }
                    }
                }
                lastIndex = index1;
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                //得到字母对应的索引
                int index = (int) (y/cellHeight);
                if(lastIndex!=index){
                    //说明当前触摸字母和上一个不是同一个字母
              LogUtil.e(tag, indexArr[index]);
                    //对index做安全性的检查
                    if(index < indexArr.length){
                        if(listener!=null){
                            listener.onTouchLetter(indexArr[index],index);
                        }
                    }
                }
                lastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(transparent);
                //重置lastIndex
                lastIndex = -1;
                break;
                default:
                    break;
        }
        invalidate();
        return true;
    }

    private int getTextHeight(String text) {
        //获取文本的高度
        Rect bounds = new Rect();
        paint.getTextBounds(text,0,text.length(), bounds);
        return bounds.height();
    }

    public void setOnTouchLetterListener(OnTouchLetterListener listener){
        this.listener = listener;
    }

    public interface OnTouchLetterListener{
        void onTouchLetter(String letter, int index);
    }
}
