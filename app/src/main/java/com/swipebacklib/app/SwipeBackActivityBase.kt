package com.swipebacklib.app

import com.swipebacklib.SwipeBackLayout

/**
 * @author Yrom
 */
interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    fun getSwipeBackLayout(): SwipeBackLayout?
    fun setSwipeBackEnable(enable: Boolean)

    /**
     * Scroll out contentView and finish the activity
     */
    fun scrollToFinishActivity()
}