package com.swipebacklib.app

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.swipebacklib.SwipeBackLayout
import com.swipebacklib.Utils

open class SwipeBackActivity : Activity(), SwipeBackActivityBase {
    private var mHelper: SwipeBackActivityHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = SwipeBackActivityHelper(this)
        mHelper!!.onActivityCreate()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper!!.onPostCreate()
    }


    override fun <T : View?> findViewById(id: Int): T {
        val v = super.findViewById<T>(id)
        return if (v == null && mHelper != null) mHelper!!.findViewById(id)!! else v
    }

    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mHelper!!.swipeBackLayout!!
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        getSwipeBackLayout().setEnableGesture(enable)
    }

    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this)
        getSwipeBackLayout().scrollToFinishActivity()
    }
}