package com.monk.moduleviews.views

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * http://blog.csdn.net/studyalllife/article/details/45529641
 * @author MonkeyHank
 * @date 2017-09-20 17:11
 */
class ScrollViewExtend @JvmOverloads constructor(context: Context?,
                                                 attrs: AttributeSet? = null,
                                                 defStyleAttr: Int = 0)
    : ScrollView(context, attrs, defStyleAttr) {

    private var onScrollChangedListener: OnScrollChangedListener? = null

    /**
     * 计算在 Y 方向上滚动的量，以便得到一个完全在屏幕上的矩形(或者，如果比屏幕高，
     * 至少是第一个屏幕大小的块)。
     * @param rect
     * @return
     */
    override fun computeScrollDeltaToGetChildRectOnScreen(rect: Rect): Int {
        super.computeScrollDeltaToGetChildRectOnScreen(rect)
        return 0
    }

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)
        if (onScrollChangedListener != null) {
            onScrollChangedListener!!.onScrollChanged(y, oldy)
        }
    }

    fun setOnScrollChangedListener(onScrollChangedListener: OnScrollChangedListener?) {
        this.onScrollChangedListener = onScrollChangedListener
    }

    interface OnScrollChangedListener {
        fun onScrollChanged(t: Int, oldl: Int)
    }
}