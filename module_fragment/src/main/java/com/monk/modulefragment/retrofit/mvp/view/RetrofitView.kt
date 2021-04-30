package com.monk.modulefragment.retrofit.mvp.view

import com.monk.modulefragment.retrofit.base.IBaseView
import com.monk.modulefragment.retrofit.bean.Bean

/**
 * @author monk
 * @date 2019-02-19
 */
interface RetrofitView : IBaseView {
    fun showProgressBar()
    fun dismissProgressBar()
    fun showError(error: String?)
    fun displayBean(bean: Bean?)
}