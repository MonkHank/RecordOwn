package com.swipebacklib.app

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.monk.aidldemo.R
import com.swipebacklib.SwipeBackLayout
import com.swipebacklib.SwipeBackLayout.SwipeListener
import com.swipebacklib.Utils

/**
 * @author Yrom
 */
class SwipeBackActivityHelper(private val mActivity: Activity) {
    var swipeBackLayout: SwipeBackLayout? = null
        private set

    fun onActivityCreate() {
        mActivity.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mActivity.window.decorView.background = null
        swipeBackLayout = LayoutInflater.from(mActivity).inflate(R.layout.swipeback_layout, null) as SwipeBackLayout
        swipeBackLayout!!.addSwipeListener(object : SwipeListener {
            override fun onScrollStateChange(state: Int, scrollPercent: Float) {}
            override fun onEdgeTouch(edgeFlag: Int) {
                Utils.convertActivityToTranslucent(mActivity)
            }

            override fun onScrollOverThreshold() {}
        })
    }

    fun onPostCreate() {
        swipeBackLayout!!.attachToActivity(mActivity)
    }

    fun  <T : View?> findViewById(id: Int): T? {
        return if (swipeBackLayout != null) {
            swipeBackLayout!!.findViewById(id)
        } else null
    }
}