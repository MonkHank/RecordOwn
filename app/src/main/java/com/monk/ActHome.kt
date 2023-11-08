package com.monk

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.monk.activity.base.BaseCompatActivity
import com.monk.aidldemo.R
import com.monk.commonutils.ToastUtils
import com.monk.commonutils.l
import com.monk.interfaces.OnRecyclerViewItemClickListener
import com.monk.interfaces.OnRecyclerViewItemClickListener2
import com.monk.jni.ActivityJni

/**
 * @author monk
 * @since 2019-05-27
 */
class ActHome : BaseCompatActivity<ActHome?>(), OnRecyclerViewItemClickListener, OnRecyclerViewItemClickListener2 {

  private var drawerLayout: DrawerLayout? = null
  private var navigationView: NavigationView? = null
  private var floatingActionButton: FloatingActionButton? = null
  private var recyclerView: RecyclerView? = null

  private val list: MutableList<HomeBean> = ArrayList()
  private var reciver: BroadcastReciver? = null
  private var isRegisterReceiver = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initToolbar(R.layout.activity_home)

    drawerLayout = findViewById(R.id.drawerLayout)
    navigationView = findViewById(R.id.navigationView)
    floatingActionButton = findViewById(R.id.fab)
    recyclerView = findViewById(R.id.recyclerView)

    navigationView!!.setCheckedItem(R.id.nav_call)
    val headerView = navigationView!!.getHeaderView(0)
    headerView.findViewById<View>(R.id.icon_image).setOnClickListener { ToastUtils.showImageToast(mContext!!, "点击了头像") }
    headerView.findViewById<View>(R.id.mail).setOnClickListener { ToastUtils.showImageToast(mContext!!, "点击了邮箱") }
    headerView.findViewById<View>(R.id.username).setOnClickListener { ToastUtils.showImageToast(mContext!!, "点击了名字") }
    navigationView!!.setNavigationItemSelectedListener { menuItem: MenuItem? ->
      // 关闭侧滑
      drawerLayout!!.closeDrawers()
      true
    }

    initRecyclerView()

    isRegisterReceiver = registScreenStatusReceiver()

    monitorNetWork()

    // TODO: "积木路由 https://www.jianshu.com/p/6649cbb8cc62"
//        Router.registerComponent("com.monk.home.applike.HomeApplike")
//        Router.registerComponent("com.monk.modulefragment.applike.FragmentApplike")
//        Router.registerComponent("com.monk.moduleviews.applike.ViewsApplike")
//        floatingActionButton!!.setOnClickListener { v: View? -> UIRouter.getInstance().openUri(this@HomeActivity, "monk://home/uirouter/demo", null) }

    floatingActionButton!!.setOnClickListener {
      ARouter.getInstance().build("/test/activity").navigation()
    }

  }

  private fun initRecyclerView() {
    val layoutManager = GridLayoutManager(this, 1)
    recyclerView!!.layoutManager = layoutManager
    list.add(HomeBean(0, "Fragment"))
    list.add(HomeBean(1, "Jni"))
    list.add(HomeBean(2, "Flutter"))
    list.add(HomeBean(6, "kill MySelf"))
    list.add(HomeBean(7, "unregisterReceiver"))
    list.add(HomeBean(9, DeviceActivity::class.java.simpleName))
    list.add(HomeBean(10, "Views"))
    list.add(HomeBean(11, "GL"))

    val homeAdapter = HomeAdapter(this, list, this)
    recyclerView!!.adapter = homeAdapter
    homeAdapter.setOnRecyclerViewItemClickListener(this)
  }

  /**
   * RecyclerView 的 itemClick 回调；不会执行，已被第二种方式拦截
   * @param view
   */
  override fun onRecyclerViewItemClick(view: View) {
    l.i(simpleName, "view:$view")
  }

  override fun onRecyclerViewItemClick(view: View, position: Int) {
    val bean = list[position]
    when (bean.Tag) {
      0 -> ARouter.getInstance().build("/module_fragment/home").navigation()
      1 -> startActivity(ActivityJni::class.java)
      2 -> startActivity(ActFlutter::class.java)
      6 -> ToastUtils.showImageToast(this@ActHome, "kill myself")
      7 -> if (isRegisterReceiver) {
        unregisterReceiver(reciver)
        isRegisterReceiver = false
      }

      9 -> startActivity(DeviceActivity::class.java)
      10 -> ToastUtils.showToast(this@ActHome, "点击了")
      11 -> ARouter.getInstance().build("/module_gl/ActMain").navigation()
    }
  }

  private fun registScreenStatusReceiver(): Boolean {
    reciver = BroadcastReciver()
    val filter = IntentFilter()
    filter.addAction(Intent.ACTION_SCREEN_ON)
    filter.addAction(Intent.ACTION_SCREEN_OFF)
    filter.addAction(Intent.ACTION_TIME_TICK)
    filter.addAction(Intent.ACTION_DATE_CHANGED)
    filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
    registerReceiver(reciver, filter)
    return true
  }

  override fun onDestroy() {
    super.onDestroy()
    unregisterReceiver(reciver)
  }

  private var lastMills: Long = 0

  override fun onBackPressed() {
    if (System.currentTimeMillis() - lastMills > 2000) {
      lastMills = System.currentTimeMillis()
      com.blankj.utilcode.util.ToastUtils.showLong("再按一次退出")
      return
    }
    finish()
  }
}