package com.monk.jni

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.monk.activity.base.BaseCompatActivity
import com.monk.aidldemo.R

class ActivityJni : BaseCompatActivity<ActivityJni?>() {
  //    static {
  //        System.loadLibrary("native-lib");
  //    }
  //
  //    public native String sayHello(String string);
  private var fragmentManager: FragmentManager? = null
  private var mCurrentFragment: Fragment? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initToolbar(R.layout.fragment_jni)
    fragmentManager = supportFragmentManager
    addAndShowFragment(JniFragment())
  }

  private fun addAndShowFragment(fragment: Fragment) {
    // FragmentTransaction表示一次完整的事务，commit() 之前的一系列连贯操作称之为一次事务。
    val ft = fragmentManager!!.beginTransaction()
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