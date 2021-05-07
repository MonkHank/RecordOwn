package com.monk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.monk.activity.base.BaseCompatActivity
import com.monk.commonutils.LogUtil
import com.monk.moduleviews.R
import com.monk.moduleviews.adapters.ViewsBean
import com.monk.moduleviews.fragments.*
import java.lang.StringBuilder

class ViewsDetailActivity:BaseCompatActivity<ViewsDetailActivity?>() {

    private var fragmentViewsEventDispatch: FragmentViewsEventDispatch? = null
    private var fragmentViewsLayoutInflater: FragmentViewLayoutInflater? = null
    private var fragmentViewsAddEquip: FragmentViewsAddEquip? = null
    private var fragmentViewsMaxHsv: FragmentViewsMaxHsv? = null
    private var fragmentViewsQQLv: FragmentViewsQQLv? = null
    private var fragmentViewPercent:FragmentViewPercent?=null
    private var mCurrentFragment: Fragment? = null

    val sb = StringBuilder()

    companion object{
        fun createIntent(context: Context?, type: String): Intent {
            val inteni = Intent(context, ViewsDetailActivity::class.java)
            inteni.putExtra("type", type)
            return inteni
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.layout.act_moduleviews_detail)

        fragmentViewsEventDispatch = FragmentViewsEventDispatch()
        fragmentViewsLayoutInflater = FragmentViewLayoutInflater()
        fragmentViewsAddEquip = FragmentViewsAddEquip()
        fragmentViewPercent = FragmentViewPercent()
        fragmentViewsMaxHsv = FragmentViewsMaxHsv()
        fragmentViewsQQLv = FragmentViewsQQLv()


        when (intent.getStringExtra("type")) {
            ViewsBean.ed -> addAndShowFragment(fragmentViewsEventDispatch!!)
            ViewsBean.lf -> addAndShowFragment(fragmentViewsLayoutInflater!!)
            ViewsBean.ae -> addAndShowFragment(fragmentViewsAddEquip!!)
            ViewsBean.pc -> addAndShowFragment(fragmentViewPercent!!)
            ViewsBean.mhv -> addAndShowFragment(fragmentViewsMaxHsv!!)
            ViewsBean.qqlv -> addAndShowFragment(fragmentViewsQQLv!!)
        }

    }

    private fun addAndShowFragment(fragment: Fragment) {
        // FragmentTransaction表示一次完整的事务，commit() 之前的一系列连贯操作称之为一次事务。
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (mCurrentFragment != null) ft.hide(mCurrentFragment!!)
        if (!fragment.isAdded) {
            ft.add(R.id.fragmentContainer, fragment, fragment.javaClass.name)
        } else {
            ft.show(fragment)
        }
        ft.commit()
        mCurrentFragment = fragment
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        fragmentViewsLayoutInflater?.onWindowFocusChanged(hasFocus)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        LogUtil.i(tag, "$simpleName：dispatchTouchEvent")
        sb.append("$simpleName：dispatchTouchEvent\n")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LogUtil.i(tag, "$simpleName：onTouchEvent")
        sb.append("$simpleName：dispatchTouchEvent\n")
        return super.onTouchEvent(event)
    }

}