package com.monk.moduleviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monk.activity.base.BaseFragment
import com.monk.moduleviews.R

class FragmentViewCustom :BaseFragment<FragmentViewCustom?>(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_module_views_custom_view,container,false)
        return view
    }

}