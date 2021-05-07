package com.monk.modulefragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.monk.activity.base.BaseFragment

class FragmentChildA : BaseFragment<FragmentChildA?>(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val textView = TextView(mActivity)
        textView.text = "FragmentChildA"
        return textView
    }


}