package com.monk.global

/**
 * @author monk
 * @date 2021/11/25下午 10:21
 */
interface IStatusView {
    fun showEmptyView() //空视图
    fun showErrorView(errMsg: String) //错误视图
    fun showLoadingView(isShow: Boolean) //展示Loading视图
}