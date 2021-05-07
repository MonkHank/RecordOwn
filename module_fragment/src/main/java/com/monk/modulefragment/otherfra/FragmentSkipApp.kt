package com.monk.modulefragment.otherfra

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.blankj.utilcode.util.ToastUtils
import com.monk.activity.base.BaseFragment
import com.monk.commonutils.IntentUtils
import com.monk.commonutils.LogUtil
import com.monk.modulefragment.R
import com.monk.modulefragment.retrofit.RetrofitUtils
import com.monk.modulefragment.retrofit.bean.Bean
import com.monk.modulefragment.retrofit.mvp.present.RetrofitPresent
import com.monk.modulefragment.retrofit.mvp.present.RetrofitPresentImpl
import com.monk.modulefragment.retrofit.mvp.view.RetrofitView

/**
 * @author monk
 * @date 2019-1-25 16:08:31
 */
class FragmentSkipApp : BaseFragment<FragmentSkipApp?>(),View.OnClickListener {


    private var btSkip: AppCompatButton? = null
    private var btSkip2: AppCompatButton? = null
    private var btSkip3: AppCompatButton? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fra_modulefra_skipapp, container, false)

        btSkip = view.findViewById(R.id.btSkip)
        btSkip2 = view.findViewById(R.id.btSkip2)
        btSkip3 = view.findViewById(R.id.btSkip3)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btSkip?.setOnClickListener(this)
        btSkip2?.setOnClickListener(this)
        btSkip3?.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btSkip -> {
                try {
                    val componentName = ComponentName("com.moho.peoplesafe", "com.moho.peoplesafe.ui.activity.LoginActivity")
                    val intent = Intent()
                    intent.putExtra("fromRecordOwn", "hello")
                    val bundle = Bundle()
                    bundle.putString("from", "bundle")
                    intent.putExtras(bundle)
                    intent.component = componentName
                    startActivity(intent)
                } catch (e: Exception) {
                    // 接收方 没有配置 action（VIEW action，或者其他指定的action）
                    e.printStackTrace()
                    ToastUtils.showShort(e.message)
                }
            }
            R.id.btSkip2 -> IntentUtils.skipCallUp(mActivity, "15105199149")
            R.id.btSkip3 -> {
                try {
                    val intent = Intent("com.moho.peoplesafe.ui.activity.LoginActivity$")
                    intent.putExtra("skip3", "skip3")
                    startActivity(intent)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    ToastUtils.showShort(e.message)
                }
            }
        }
    }



}