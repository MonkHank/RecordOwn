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

/**
 * @author monk
 * @date 2019-05-29
 */
class FragmentA : BaseFragment<FragmentA?>() ,View.OnClickListener{

    private lateinit var tv1:TextView
    private lateinit var tv2:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fra_modulefra_a,container,false)

        tv1 = view.findViewById(R.id.tv1)
        tv2 = view.findViewById(R.id.tv2)

        tv1.text = "FragmentA,点我试试"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (mListener!=null){
            val uriPath = Constant.URI_SCKEME+context?.packageName+"/"+FragmentA::class.simpleName
            mListener?.onFragmentInteraction(Uri.parse(uriPath))
        }
    }
}