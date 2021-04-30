package com.monk.modulefragment.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.monk.activity.base.BaseFragment
import com.monk.global.Constant
import com.monk.modulefragment.R
import com.monk.modulefragment.otherfra.FragmentLocation
import com.monk.modulefragment.otherfra.FragmentRetrofit2

/**
 * @author monk
 * @date 2019-05-29
 */
class FragmentOher : BaseFragment<FragmentOher?>(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fra_modulefra_other, container, false)
        view.findViewById<TextView>(R.id.location).setOnClickListener(this)
        view.findViewById<TextView>(R.id.retrofit).setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.location -> {
                val uriPath=Constant.URI_SCKEME+context?.packageName+"/"+FragmentLocation::class.simpleName
                mListener?.onFragmentInteraction(Uri.parse(uriPath))
            }
            R.id.retrofit->{
                val uriPath=Constant.URI_SCKEME+context?.packageName+"/"+FragmentRetrofit2::class.simpleName
                mListener?.onFragmentInteraction(Uri.parse(uriPath))
            }
        }
    }
}