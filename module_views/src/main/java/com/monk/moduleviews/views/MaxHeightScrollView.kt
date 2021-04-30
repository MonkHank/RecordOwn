package com.monk.moduleviews.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.widget.ScrollView
import com.monk.commonutils.LogUtil

/**
 * @author monk
 * @date 2019-01-25
 */
class MaxHeightScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ScrollView(context, attrs) {
    private val tag = "MaxHeightScrollView"

    init {
        // 方式1
        val dm = context.resources.displayMetrics
        val sb = "heightPixels:${dm.heightPixels}\t\twidthPixels:${dm.widthPixels}\txDpi:${dm.xdpi}\tyDpi:${dm.ydpi}\tdensity:${dm.density}\t	densityDpi:${dm.densityDpi}	"
        LogUtil.i(tag, sb)
        LogUtil.v(tag, "toString():$dm")

        // 方式2
        val display = (context as Activity).windowManager.defaultDisplay
        val displayMetrics1 = DisplayMetrics()
        display.getMetrics(displayMetrics1)
        LogUtil.v(tag, "toString():$displayMetrics1")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(340, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}