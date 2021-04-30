package com.monk.moduleviews.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

/**
 * 2018-01-08 配合ScrollView使用的ListView，避免在ScrollView中listView只出现一个item 的情况
 * 这个listView是不支持上下滑动的
 * @author Monke 2017-09-20 14:06yHank
 * @date
 */
class ScrollListView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ListView(context, attrs, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //此处是代码的关键
        //MeasureSpec.AT_MOST的意思就是wrap_content
        //Integer.MAX_VALUE >> 2 是使用最大值的意思,也就表示的无边界模式
        //Integer.MAX_VALUE >> 2 此处表示是父布局能够给他提供的大小
        val expandSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}