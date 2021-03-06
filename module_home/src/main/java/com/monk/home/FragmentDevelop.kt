package com.monk.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.monk.activity.base.BaseFragment

class FragmentDevelop : BaseFragment<FragmentDevelop?>() {

    companion object {
        @JvmStatic
        fun newFragment(): Fragment {
            return FragmentDevelop()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


}