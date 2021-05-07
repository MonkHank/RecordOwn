package com.monk.moduleviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monk.activity.base.BaseFragment
import com.monk.moduleviews.R

class FragmentViewsMaxHsv :BaseFragment<FragmentViewsMaxHsv?>(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fra_moduleviews_maxhsv,container,false)
    }

}