package com.monk.modulefragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.monk.activity.base.BaseFragment

/**
 * @author monk
 * @date 2019-05-29
 */
class FragmentC : BaseFragment<FragmentC?>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val textView = TextView(mActivity)
        textView.text = "FragmentC"
        return textView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}