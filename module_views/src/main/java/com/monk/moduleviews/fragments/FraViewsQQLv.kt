package com.monk.moduleviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.blankj.utilcode.util.ToastUtils
import com.monk.activity.base.BaseFragment
import com.monk.moduleviews.R
import com.monk.moduleviews.views.QQListView

class FraViewsQQLv : BaseFragment<FraViewsQQLv?>(){

    private var qqListView: QQListView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fra_moduleviews_qqlv, container, false)
        qqListView = view.findViewById(R.id.qqListView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val arrays = arrayOf("jack", "nancy", "monk", "peter", "tome", "jerry", "McGrady", "Carter", "YaoMing")
        val adapter = ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, arrays)

        qqListView!!.adapter = adapter
        qqListView!!.setDelButtonClickListener(object : QQListView.DelButtonClickListener{
            override fun clickHappend(position: Int) {
               ToastUtils.showShort("position$position")
            }
        })

        qqListView!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            ToastUtils.showShort("position$position")
        }
    }

}