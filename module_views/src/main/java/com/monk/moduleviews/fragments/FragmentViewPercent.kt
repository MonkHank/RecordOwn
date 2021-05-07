package com.monk.moduleviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monk.activity.base.BaseFragment
import com.monk.moduleviews.R

class FragmentViewPercent :BaseFragment<FragmentViewPercent?>(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fra_moduleviews_pcv,container,false)
        return view
    }

}