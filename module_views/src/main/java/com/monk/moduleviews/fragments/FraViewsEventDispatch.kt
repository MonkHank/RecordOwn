package com.monk.moduleviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.monk.moduleviews.ViewsDetailActivity
import com.monk.activity.base.BaseFragment
import com.monk.moduleviews.R

class FraViewsEventDispatch : BaseFragment<FraViewsEventDispatch?>(){

    private lateinit var btMessage: AppCompatButton
    private lateinit var tvMsg: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fra_moduleviews_eventdispatch, container, false)
        btMessage = view.findViewById(R.id.btMessage)
        tvMsg = view.findViewById(R.id.tvMsg)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val msg = (mActivity as ViewsDetailActivity).sb.toString()
        tvMsg.text=msg

        btMessage.text="显示"
        btMessage.setOnClickListener {
            val sb = (mActivity as ViewsDetailActivity).sb
            if (btMessage.text=="显示"){
                btMessage.text="清除"
                tvMsg.text = sb.toString()
            }else{
                btMessage.text="显示"
                sb.clear()
                tvMsg.text =""
            }
        }
    }
}