package com.monk.moduleviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monk.activity.base.BaseFragment
import com.monk.moduleviews.R

class FraCoordinatorLayout : BaseFragment<FraCoordinatorLayout?>(){


    companion object {
        fun newFra(): FraCoordinatorLayout {
            return FraCoordinatorLayout()
        }
    }

    /**
     * app:layout_scrollFlags  - AppBarLayout的直接子控件可以设置的属性:layout_scrollFlags
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fra_moduleviews_coordinatorlayout, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}