package com.monk.moduleviews.views

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import com.monk.commonutils.LogUtil
import com.monk.moduleviews.R

/**
 * @author MonkeyHank
 * @date 2017-09-22 09:39
 */
class QQListView(context: Context?, attrs: AttributeSet?) : ListView(context, attrs) {
    private val tag = "QQListView"

    /*** 用户滑动的最小距离 */
    private val touchSlop: Int

    /***  是否响应滑动 */
    private var isSliding = false
    private val mPopupWindow: PopupWindow?
    private val mPopupWindowHeight: Int
    private val mPopupWindowWidth: Int
    private val mDelBtn: Button
    private var mListener: DelButtonClickListener? = null

    /*** 当前手指触摸的View */
    private var mCurrentView: View? = null

    /*** 当前手指触摸的位置 */
    private var mCurrentViewPos = 0

    init {
        val mInflater = LayoutInflater.from(context)

        // todo 需要研究一下
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
        val view = mInflater.inflate(R.layout.delete_btn, null)
        mDelBtn = view.findViewById(R.id.id_item_btn)
        mPopupWindow = PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        // 获取popupWindow宽高
        mPopupWindow.contentView.measure(0, 0)
        mPopupWindowHeight = mPopupWindow.contentView.measuredHeight
        mPopupWindowWidth = mPopupWindow.contentView.measuredWidth
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        LogUtil.i(tag, "dispatchTouchEvent:$action")
        var xDown = 0
        var yDown = 0
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                xDown = ev.x.toInt()
                yDown = ev.y.toInt()
                if (mPopupWindow!!.isShowing) {
                    dismissPopWindow()
                    return false
                }
                // 获得当前手指按下时的item的位置
                mCurrentViewPos = pointToPosition(xDown, yDown)
                LogUtil.i(tag, "currentViewPos:$mCurrentViewPos")
                val firstVisiblePosition = firstVisiblePosition
                // 获得当前手指按下时的item
                mCurrentView = getChildAt(mCurrentViewPos - firstVisiblePosition)
                LogUtil.i(tag, "fistVisiblePosition:$firstVisiblePosition")
            }
            MotionEvent.ACTION_MOVE -> {
                val xMove = ev.x.toInt()
                val yMove = ev.y.toInt()
                val dx = xMove - xDown
                val dy = yMove - yDown
                if (xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop) {
                    LogUtil.e(tag, "touchslop = $touchSlop , dx = $dx , dy = $dy")
                    isSliding = true
                }
            }
            else -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        LogUtil.i(tag, "onTouchEvent:$action")
        if (isSliding) {
            when (action) {
                MotionEvent.ACTION_MOVE -> {
                    val location = IntArray(2)
                    // 获得当前item的位置x与y
                    mCurrentView!!.getLocationOnScreen(location)

                    // 设置popupWindow的动画
                    mPopupWindow!!.animationStyle = android.R.style.Animation_Toast
                    mPopupWindow.update()
                    mPopupWindow.showAtLocation(mCurrentView, Gravity.LEFT or Gravity.TOP,
                            location[0] + mCurrentView!!.width, location[1] + mCurrentView!!.height / 2
                            - mPopupWindowHeight / 2)
                    // 设置删除按钮的回调
                    mDelBtn.setOnClickListener {
                        if (mListener != null) {
                            mListener!!.clickHappend(mCurrentViewPos)
                            mPopupWindow.dismiss()
                        }
                    }
                }
                MotionEvent.ACTION_UP -> isSliding = false
                else -> {
                }
            }
            // 相应滑动期间屏幕itemClick事件，避免发生冲突
            return true
        }
        return super.onTouchEvent(ev)
    }

    private fun dismissPopWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing) {
            mPopupWindow.dismiss()
        }
    }

    fun setDelButtonClickListener(listener: DelButtonClickListener?) {
        mListener = listener
    }

    interface DelButtonClickListener {
        fun clickHappend(position: Int)
    }
}