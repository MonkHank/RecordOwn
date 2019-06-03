package com.peoplesafe.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * http://blog.csdn.net/studyalllife/article/details/45529641
 * @author MonkeyHank
 * @date 2017-09-20 17:11
 */

public class ScrollViewExtend extends ScrollView {
    private OnScrollChangedListener onScrollChangedListener;

    public ScrollViewExtend(Context context) {
        this(context,null);
    }

    public ScrollViewExtend(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollViewExtend(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 计算在 Y 方向上滚动的量，以便得到一个完全在屏幕上的矩形(或者，如果比屏幕高，
     * 至少是第一个屏幕大小的块)。
     * @param rect
     * @return
     */
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        super.computeScrollDeltaToGetChildRectOnScreen(rect);
        return 0;
    }


    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(this.onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(y, oldy);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    public interface OnScrollChangedListener{
        void onScrollChanged(int t, int oldl);
    }

}
