package com.monk.modulefragment.otherfra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.monk.activity.base.BaseFragment
import com.monk.modulefragment.R
import com.monk.modulefragment.retrofit.RetrofitUtils
import com.monk.modulefragment.retrofit.bean.Bean
import com.monk.modulefragment.retrofit.mvp.present.RetrofitPresent
import com.monk.modulefragment.retrofit.mvp.present.RetrofitPresentImpl
import com.monk.modulefragment.retrofit.mvp.view.RetrofitView

/**
 * @author monk
 * @date 2019-1-25 16:08:31
 */
class FragmentRetrofit2 : BaseFragment<FragmentRetrofit2?>(), RetrofitView {
    private var tvRetrofit: TextView? = null

    private var retrofitPresent: RetrofitPresent? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fra_modulefra_retrofit, container, false)
        tvRetrofit = view.findViewById(R.id.tvMsg)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val instance: RetrofitUtils = RetrofitUtils.getInstance()
        instance.create()
        instance.asyncFun()

        retrofitPresent = RetrofitPresentImpl(this)
        // 协信
        retrofitPresent?.getCopyList("8198b9a7-9f53-4932-a197-c76bac59ed4f")

    }

    override fun showProgressBar() {
    }
    override fun dismissProgressBar() {
    }
    override fun showError(error: String?) {
    }
    override fun displayBean(bean: Bean?) {
    }
}