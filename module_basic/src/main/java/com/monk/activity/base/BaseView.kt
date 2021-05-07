package com.monk.activity.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.monk.commonutils.LogUtil

/**
 * @author monk
 * @date 2019-06-05
 */
open class BaseView<T : BaseView<T>?> @JvmOverloads constructor(context: Context?,
                                                                attrs: AttributeSet? = null,
                                                                defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    protected var tag: String
    protected var simpleName: String

    override fun onFinishInflate() {
        super.onFinishInflate()
        LogUtil.i(tag, simpleName + "onFinishInflate")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        LogUtil.i(tag, simpleName + "onAttachedToWindow")
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        LogUtil.i(tag, simpleName + "onWindowVisibilityChanged")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        LogUtil.i(tag, simpleName + "onMeasure")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LogUtil.i(tag, simpleName + "onSizeChanged")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        LogUtil.i(tag, simpleName + "onLayout")
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        LogUtil.i(tag, simpleName + "onWindowFocusChanged")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        LogUtil.e(tag, simpleName + "onDetachedFromWindow")
    }

    init {
        val mView = this
        simpleName = mView.javaClass.simpleName + "："
        tag = mView.javaClass.simpleName
    }
}