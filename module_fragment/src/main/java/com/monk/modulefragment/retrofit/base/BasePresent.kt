package com.monk.modulefragment.retrofit.base

/**
 * @author monk
 * @date 2019-02-19
 */
open class BasePresent<T : IBaseView?>(mMvpView: T) {
    var mvpView: T?
        protected set

    fun detachMvpView() {
        mvpView = null
    }

    init {
        mvpView = mMvpView
    }
}