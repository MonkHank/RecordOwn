package com.monk.jni

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.monk.activity.base.BaseFragment
import com.monk.aidldemo.R
import com.monk.commonutils.LogUtil

/**
 * @author monk
 * @date 2019-1-22 15:15:33
 */
class JniFragment : BaseFragment<JniFragment?>() {
    private val tagg = "JniFragment"

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun sayHello(string: String?): String?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        LogUtil.i(tagg, "\ninflater = $inflater\ncontainer = $container\nbundle = $savedInstanceState")
        val view = inflater.inflate(R.layout.fragment_jni, container, false)
        view.findViewById<View>(R.id.fragmentContainer).visibility = View.GONE
        val tvJni = view.findViewById<TextView>(R.id.tvJni)
        val lp = tvJni.layoutParams as LinearLayout.LayoutParams
        lp.topMargin = 100
        lp.leftMargin = 100
        tvJni.layoutParams = lp
        tvJni.text = sayHello("成功调用jni")
        return view
    }
}