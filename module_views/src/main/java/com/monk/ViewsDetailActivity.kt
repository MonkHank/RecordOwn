package com.monk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.monk.activity.base.BaseCompatActivity
import com.monk.moduleviews.R
import com.monk.moduleviews.fragments.FragmentViewCustom
import com.monk.moduleviews.fragments.FragmentViewsA

class ViewsDetailActivity:BaseCompatActivity<ViewsDetailActivity?>() {


    private var fragmentViewsA: FragmentViewsA? = null
    private var fragmentViewCustom:FragmentViewCustom?=null
    private var mCurrentFragment: Fragment? = null

    companion object{
        fun createIntent(context:Context?,type:Int): Intent {
            val inteni = Intent(context, ViewsDetailActivity::class.java)
            inteni.putExtra("type",type)
            return inteni
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.layout.activity_module_views_detail)

        fragmentViewsA = FragmentViewsA()
        fragmentViewCustom = FragmentViewCustom()


        when (intent.getIntExtra("type",0)) {
            0-> addAndShowFragment(fragmentViewsA!!)
            1-> addAndShowFragment(fragmentViewCustom!!)
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

}