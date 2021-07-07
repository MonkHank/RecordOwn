package com.monk.moduleviews

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.monk.activity.base.BaseCompatActivity
import com.monk.commonutils.LogUtil
import com.monk.moduleviews.adapters.ViewsBean
import com.monk.moduleviews.fragments.*

class ViewsDetailActivity: BaseCompatActivity<ViewsDetailActivity?>() {

    private var fraViewsEventDispatch: FraViewsEventDispatch? = null
    private var fraViewsLayoutInflater: FraViewLayoutInflater? = null
    private var fraViewsAddEquip: FraViewsAddEquip? = null
    private var fraViewsMaxHsv: FraViewsMaxHsv? = null
    private var fraViewsQQLv: FraViewsQQLv? = null
    private var fraViewPercent:FraViewPercent?=null
    private var fraCoordinatorLayout: FraCoordinatorLayout? = null
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

        fraViewsEventDispatch = FraViewsEventDispatch()
        fraViewsLayoutInflater = FraViewLayoutInflater()
        fraViewsAddEquip = FraViewsAddEquip()
        fraViewPercent = FraViewPercent()
        fraViewsMaxHsv = FraViewsMaxHsv()
        fraViewsQQLv = FraViewsQQLv()
        fraCoordinatorLayout = FraCoordinatorLayout.newFra()


        when (intent.getStringExtra("type")) {
            ViewsBean.ed -> addAndShowFragment(fraViewsEventDispatch!!)
            ViewsBean.lf -> addAndShowFragment(fraViewsLayoutInflater!!)
            ViewsBean.ae -> addAndShowFragment(fraViewsAddEquip!!)
            ViewsBean.pc -> addAndShowFragment(fraViewPercent!!)
            ViewsBean.mhv -> addAndShowFragment(fraViewsMaxHsv!!)
            ViewsBean.qqlv -> addAndShowFragment(fraViewsQQLv!!)
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
        fraViewsLayoutInflater?.onWindowFocusChanged(hasFocus)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
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