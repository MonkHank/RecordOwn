package com.monk.customview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ScrollView;

import com.monk.utils.LogUtil;

/**
 * @author monk
 * @date 2019-01-25
 */
public class MaxHeightScrollView extends ScrollView {
    private final String tag = "MaxHeightScrollView";

    public MaxHeightScrollView(Context context) {
        this(context, null);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 方式1
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        String sb = "heightPixels:" + dm.heightPixels + "\t" +
                "widthPixels:" +  dm.widthPixels + "\n" +
                "xDpi:" + dm.xdpi + "\t" +
                "yDpi:" + dm.ydpi + "\n" +
                "density:" + dm.density + "\t" +
                "densityDpi:" + dm.densityDpi + "\t";
        LogUtil.i(tag, sb);
        LogUtil.v(tag, "toString():" + dm.toString());

        // 方式2
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        display.getMetrics(displayMetrics1);
        LogUtil.v(tag, "toString():" + displayMetrics1.toString());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(340, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
