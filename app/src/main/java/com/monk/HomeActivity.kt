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
import butterknife.BindView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.luojilab.component.componentlib.router.Router
import com.luojilab.component.componentlib.router.ui.UIRouter
import com.monk.activity.DeviceActivity
import com.monk.activity.base.BaseCompatActivity
import com.monk.aidldemo.R
import com.monk.broadcast.BroadcastReciver
import com.monk.commonutils.LogUtil
import com.monk.commonutils.ToastUtils
import com.monk.jni.ActivityJni
import com.monk.ui.HomeBean
import com.monk.ui.adapter.HomeAdapter
import com.monk.ui.interfaces.OnRecyclerViewItemClickListener
import com.monk.ui.interfaces.OnRecyclerViewItemClickListener2
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * @author monk
 * @date 2019-05-27
 */
class HomeActivity : BaseCompatActivity<HomeActivity?>()
        , OnRecyclerViewItemClickListener
        , OnRecyclerViewItemClickListener2 {
    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var floatingActionButton: FloatingActionButton? = null
    var recyclerView: RecyclerView? = null

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
        headerView.findViewById<View>(R.id.icon_image).setOnClickListener { v: View? -> ToastUtils.showImageToast(mContext!!, "点击了头像") }
        headerView.findViewById<View>(R.id.mail).setOnClickListener { v: View? -> ToastUtils.showImageToast(mContext!!, "点击了邮箱") }
        headerView.findViewById<View>(R.id.username).setOnClickListener { v: View? -> ToastUtils.showImageToast(mContext!!, "点击了名字") }
        navigationView!!.setNavigationItemSelectedListener { menuItem: MenuItem? ->
            // 关闭侧滑
            drawerLayout!!.closeDrawers()
            true
        }
        val layoutManager = GridLayoutManager(this, 1)
        recyclerView!!.layoutManager = layoutManager
        list.add(HomeBean(0, "Fragment"))
        list.add(HomeBean(1, "Jni"))
        list.add(HomeBean(2, "Flutter"))
        list.add(HomeBean(6, "kill MySelf"))
        list.add(HomeBean(7, "unregisterReceiver"))
        list.add(HomeBean(9, DeviceActivity::class.java.simpleName))
        list.add(HomeBean(10, "Views"))

        val homeAdapter = HomeAdapter(this, list, this)
        recyclerView!!.adapter = homeAdapter
        homeAdapter.setOnRecyclerViewItemClickListener(this)

        isRegisterReceiver = registScreenStatusReceiver()
        monitorNetWork()
        EventBus.getDefault().postSticky(Any())
        Router.registerComponent("com.monk.home.applike.HomeApplike")
        Router.registerComponent("com.monk.modulefragment.applike.FragmentApplike")
        Router.registerComponent("com.monk.moduleviews.applike.ViewsApplike")
        floatingActionButton!!.setOnClickListener { v: View? -> UIRouter.getInstance().openUri(this@HomeActivity, "monk://home/uirouter/demo", null) }
    }

    /**
     * RecyclerView 的 itemClick 回调；不会执行，已被第二种方式拦截
     * @param view
     */
    override fun onRecyclerViewItemClick(view: View) {
        LogUtil.i(simpleName, "view:$view")
    }

    override fun onRecyclerViewItemClick(view: View, position: Int) {
        val bean = list[position]
        when (bean.Tag) {
            0 -> UIRouter.getInstance().openUri(this@HomeActivity, "monk://modulefragment/main", null)
            1 -> startActivity(ActivityJni::class.java)
            2 -> startActivity(ActFlutter::class.java)
            6 -> {
            }
            7 -> if (isRegisterReceiver) {
                unregisterReceiver(reciver)
                isRegisterReceiver = false
            }
            9 -> startActivity(DeviceActivity::class.java)
            10 -> UIRouter.getInstance().openUri(this@HomeActivity, "monk://moduleviews/views/main", null)
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