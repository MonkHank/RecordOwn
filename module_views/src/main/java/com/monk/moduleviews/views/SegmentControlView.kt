package com.monk.moduleviews.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import android.widget.TextView
import com.monk.moduleviews.R
import org.xmlpull.v1.XmlPullParser

/**
 * @author JackieHank
 * @date 2017-09-08 16:09.
 */
class SegmentControlView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
    : LinearLayout(context, attrs, defStyleAttr) {

    private var textView1: TextView? = null
    private var textView2: TextView? = null

    constructor(context: Context?) : this(context, null) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {
        initView()
    }

    private fun initView() {
        textView1 = TextView(context)
        textView2 = TextView(context)
        textView1!!.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        textView2!!.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        textView1!!.text = "错题"
        textView2!!.text = "收藏"
        try {
            val xrp: XmlPullParser = resources.getXml(R.xml.selector_segmentcontrol)
            val csl = ColorStateList.createFromXml(resources, xrp)
            textView1!!.setTextColor(csl)
            textView2!!.setTextColor(csl)
        } catch (e: Exception) {
        }
        textView1!!.gravity = Gravity.CENTER
        textView2!!.gravity = Gravity.CENTER
        textView1!!.setPadding(dp2Px(context, 10f), dp2Px(context, 5f), dp2Px(context, 10f), dp2Px(context, 5f))
        textView2!!.setPadding(dp2Px(context, 10f), dp2Px(context, 5f), dp2Px(context, 10f), dp2Px(context, 5f))
        setSegmentTextSize(18)
        textView1!!.setBackgroundResource(R.drawable.segment_left)
        textView2!!.setBackgroundResource(R.drawable.segment_right)
        textView1!!.isSelected = true
        removeAllViews()
        this.addView(textView1)
        this.addView(textView2)
        this.invalidate()
        textView1!!.setOnClickListener(OnClickListener {
            if (textView1!!.isSelected) {
                return@OnClickListener
            }
            textView1!!.isSelected = true
            textView2!!.isSelected = false
            if (listener != null) {
                listener!!.onSegmentViewClick(textView1, 0)
            }
        })
        textView2!!.setOnClickListener(OnClickListener {
            if (textView2!!.isSelected) {
                return@OnClickListener
            }
            textView2!!.isSelected = true
            textView1!!.isSelected = false
            if (listener != null) {
                listener!!.onSegmentViewClick(textView2, 1)
            }
        })
    }

    /**
     * 设置字体大小 单位dip
     *
     * 2014年7月18日
     * @param dp
     * @author RANDY.ZHANG
     */
    fun setSegmentTextSize(dp: Int) {
        textView1!!.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat())
        textView2!!.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat())
    }

    private var listener: onSegmentViewClickListener? = null
    fun setOnSegmentViewClickListener(listener: onSegmentViewClickListener?) {
        this.listener = listener
    }

    /**
     * 设置文字
     *
     * 2014年7月18日
     * @param text
     * @param position
     * @author RANDY.ZHANG
     */
    fun setSegmentText(text: CharSequence?, position: Int) {
        if (position == 0) {
            textView1!!.text = text
        }
        if (position == 1) {
            textView2!!.text = text
        }
    }

    interface onSegmentViewClickListener {
        /**
         *
         *
         * 2014年7月18日
         * @param v
         * @param position 0-左边 1-右边
         * @author RANDY.ZHANG
         */
        fun onSegmentViewClick(v: View?, position: Int)
    }

    companion object {
        private fun dp2Px(context: Context, dp: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }

    init {
        initView()
    }
}