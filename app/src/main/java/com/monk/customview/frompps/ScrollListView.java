package com.monk.customview.frompps;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 2018-01-08 配合ScrollView使用的ListView，避免在ScrollView中listView只出现一个item 的情况
 *  这个listView是不支持上下滑动的
 * @author Monke 2017-09-20 14:06yHank
 * @date
 */

public class ScrollListView extends ListView {
    public ScrollListView(Context context) {
        this(context,null);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //此处是代码的关键
        //MeasureSpec.AT_MOST的意思就是wrap_content
        //Integer.MAX_VALUE >> 2 是使用最大值的意思,也就表示的无边界模式
        //Integer.MAX_VALUE >> 2 此处表示是父布局能够给他提供的大小
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
