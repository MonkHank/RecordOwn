package com.monk.modulefragment

import android.content.UriMatcher
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.luojilab.router.facade.annotation.RouteNode
import com.monk.activity.base.BaseCompatActivity
import com.monk.activity.base.OnFragmentInteractionListener
import com.monk.commonutils.LogUtil
import com.monk.modulefragment.fragment.FragmentA
import com.monk.modulefragment.fragment.FragmentB
import com.monk.modulefragment.fragment.FragmentC
import com.monk.modulefragment.fragment.FragmentD

@RouteNode(path = "/main", desc = "主页")
class FragmentsActivity : BaseCompatActivity<FragmentsActivity?>(),
        OnFragmentInteractionListener,
        View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var tvMessage: TextView
    lateinit var navigation: BottomNavigationView

    private var fragmentManager: FragmentManager? = null
    private var fragmentA: Fragment? = null
    private var fragmentB: Fragment? = null
    private var fragmentC: Fragment? = null
    private var fragmentD: Fragment? = null
    private var mCurrentFragment: Fragment? = null

    private var uriMatcher: UriMatcher? = null
    private val customViewFragment = 0
    private val customViewFragment2 = 1
    private val locationFragment = 3
    private var menuItemItemId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.layout.activity_module_fragment_main)
        tvMessage = findViewById(R.id.message)
        navigation = findViewById(R.id.navigation)
        findViewById<View>(R.id.btTestAttach).setOnClickListener(this)
        findViewById<View>(R.id.btAdd).setOnClickListener(this)
        navigation.setOnNavigationItemSelectedListener(this)
        fragmentManager = supportFragmentManager
        if (savedInstanceState != null) {
            fragmentA = fragmentManager!!.findFragmentByTag(FragmentA::class.java.name)
            fragmentB = fragmentManager!!.findFragmentByTag(FragmentB::class.java.name)
            fragmentC = fragmentManager!!.findFragmentByTag(FragmentC::class.java.name)
            fragmentD = fragmentManager!!.findFragmentByTag(FragmentD::class.java.name)
            menuItemItemId = savedInstanceState.getInt("menuItemItemId")
            navigation.selectedItemId = menuItemItemId
        } else {
            // TODO: 2019-05-29 如果menuItemItemId 和其它resId一样，这样就不行了
            navigation.setSelectedItemId(R.id.navigation_sample)
        }
        initUri()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItemItemId = menuItem.itemId
        if (menuItem.itemId == R.id.navigation_sample) {
            if (fragmentA == null) {
                fragmentA = FragmentA()
            }
            addAndShowFragment(fragmentA!!)
            return true
        }
        if (menuItem.itemId == R.id.navigation_home) {
            if (fragmentB == null) {
                fragmentB = FragmentB()
            }
            addAndShowFragment(fragmentB!!)
            return true
        }
        if (menuItem.itemId == R.id.navigation_jni) {
            if (fragmentC == null) {
                fragmentC = FragmentC()
            }
            addAndShowFragment(fragmentC!!)
            return true
        }
        if (menuItem.itemId == R.id.navigation_custom_view) {
            if (fragmentD == null) {
                fragmentD = FragmentD()
            }
            addAndShowFragment(fragmentD!!)
            return true
        }
        return false
    }

    private fun addAndShowFragment(fragment: Fragment) {
        // FragmentTransaction表示一次完整的事务，commit() 之前的一系列连贯操作称之为一次事务。
        val ft = fragmentManager!!.beginTransaction()
        if (mCurrentFragment != null) {
            ft.hide(mCurrentFragment!!)
        }
        if (!fragment.isAdded) {
            ft.add(R.id.fragmentContainer, fragment, fragment.javaClass.name)
        } else {
            ft.show(fragment)
        }
        ft.commit()
        mCurrentFragment = fragment
    }

    private fun initUri() {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        //        uriMatcher.addURI(getPackageName(), CustomViewFragment.class.getName(), customViewFragment);
//        uriMatcher.addURI(getPackageName(), CustomViewFragment2.class.getName(), customViewFragment2);
//        uriMatcher.addURI(getPackageName(), RxJava2Fragment.class.getName(), locationFragment);
    }

    override fun onFragmentInteraction(uri: Uri) {
        val match = uriMatcher!!.match(uri)
        val ft = supportFragmentManager.beginTransaction()
        LogUtil.v(simpleName, "$uri- - - $match")
    }

    override fun onClick(v: View) {
        val ft = supportFragmentManager.beginTransaction()
        if (v.id == R.id.btTestAttach) {
            if (fragmentA!!.isAdded) {
                ft.detach(fragmentA!!).commit()
            } else {
                ft.attach(fragmentA!!).commit()
            }
            LogUtil.i(tag, "sampleFragment.isAdded()：" + fragmentA!!.isAdded)
            LogUtil.i(tag, "sampleFragment.isDetach()：" + fragmentA!!.isDetached)
            return
        }
        if (v.id == R.id.btAdd) {
            ft.replace(R.id.fragmentContainer, fragmentA!!).commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("menuItemItemId", menuItemItemId)
        val fragments = supportFragmentManager.fragments
        for (t in fragments) {
            LogUtil.w(tag, "fragment:" + t.javaClass.simpleName)
        }
    }
}