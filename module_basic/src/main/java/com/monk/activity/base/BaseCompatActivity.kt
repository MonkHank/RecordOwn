package com.monk.activity.base

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import butterknife.ButterKnife
import com.gyf.immersionbar.ImmersionBar
import com.luojilab.component.componentlib.service.AutowiredService
import com.monk.basic.R
import com.monk.commonutils.LogUtil
import com.monk.commonutils.ToastUtils
import kotlinx.coroutines.GlobalScope
import okhttp3.OkHttp

/**
 * @author monk
 * @date 2019-05-27
 */
open class BaseCompatActivity<T : BaseCompatActivity<T>?> : AppCompatActivity() {
    protected var tag: String? = null
    protected var immersionBar: ImmersionBar? = null
    protected var mContext: BaseCompatActivity<T>? = null
    protected var simpleName: String? = null

    /**
     * 该方法会自动传入一个 Bundle 对象, 该 Bundle 对象就是上次被系统销毁时在 onSaveInstanceState 或者
     * onRestoreInstanceState 中保存的数据
     * -- 注意 : 只有是系统自动回收的时候才会保存 Bundle 对象数据；默认为null
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        simpleName = mContext!!.javaClass.simpleName
        tag = simpleName
        LogUtil.i(tag, "$simpleName：savedInstanceState = $savedInstanceState")
        immersionBar = ImmersionBar.with(this) //                .statusBarDarkFont(true, 0.2f)// 白色状态栏透明方案
                .keyboardEnable(true)
        //        immersionBar.init();
        fullScreen(mContext!!)
        AutowiredService.Factory.getSingletonImpl().autowire(this)
    }

    protected fun initToolbar(layoutResId: Int) {
        setContentView(layoutResId)
        ButterKnife.bind(this)
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        if (toolBar != null) {
            setSupportActionBar(toolBar)
            val actionBar = supportActionBar
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.title = simpleName
            }
        }
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private fun fullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                val window = activity.window
                val decorView = window.decorView
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                decorView.systemUiVisibility = option
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                val window = activity.window
                val attributes = window.attributes
                val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                val flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                attributes.flags = attributes.flags or flagTranslucentStatus
                //                attributes.flags |= flagTranslucentNavigation;
                window.attributes = attributes
            }
        }
    }

    fun startActivity(cls: Class<out BaseCompatActivity<*>?>?) {
        startActivity(Intent(mContext, cls))
    }

    /** 点击actionBar返回按钮 */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        LogUtil.v(tag, "$simpleName：onNewIntent(Intent)")
    }

    /**
     * 官方建使用 onRestoreInstanceState() 来恢复数据而不是 onCreate()，
     * 所以在 onStart() 方法不要做其他事情
     */
    override fun onStart() {
        super.onStart()
        LogUtil.i(tag, "$simpleName：onStart()")
    }

    /**
     *
     *
     * 方法回调时机 : 在 Activity 被系统销毁之后 恢复 Activity 时被调用, 只有销毁了之后重建的时候才调用,
     * 如果内存充足, 系统没有销毁这个 Activity, 就不需要调用;
     *
     *
     *
     *
     * 横竖屏切换，不设置 android:configuration 时候，会执行这个方法
     *
     *
     * @param savedInstanceState
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtil.v(tag, "$simpleName：onRestoreInstanceState(Bundle)")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.i(tag, "$simpleName：onResume()")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        LogUtil.v(tag, "$simpleName：onWindowFocusChanged() -- $hasFocus")
    }

    /**
     * 如果执行，那么是在 onPause()之前执行
     */
    override fun finish() {
        super.finish()
        LogUtil.e(tag, "$simpleName：finish()")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.e(tag, "$simpleName：onPause()")
    }

    /**
     * 数据保存 : Activity 声明周期结束的时候, 需要保存 Activity 状态的时候,
     * 会将要保存的数据使用键值对的形式 保存在 Bundle 对象中;
     *
     *
     * 恢复数据 : 在 Activity 的 onCreate()方法 创建 Activity 的时候会传入一个 Bundle 对象,
     * 这个 Bundle 对象就是这个 outState 参数;
     *
     *
     *
     * Activity 容易被销毁的时候调用, 注意是容易被销毁, 也可能没有销毁就调用了;
     * ①home键、②电源键、③启动其它Activity、④横竖屏切换；
     * 用户主动销毁不会调用，比如回退键，或者调用finish()；
     * 调用时机不固定，一定是在onStop()之前，但不确定是否在onPause()之前还是之后；
     * 只有有id的组件才会保存；
     *
     *
     *
     *
     * 对于Fragment 来说，如果 commit() 函数在 onSaveInstance() 之后执行，那么会抛出异常
     * IllegalStateException: Can not perform this action after onSaveInstanceState
     * 避免方案：
     * - 不要把Fragment事务放在异步线程的回调中，比如不要把Fragment事务放在AsyncTask的onPostExecute()，
     * 因此 onPostExecute() 可能会在 onSaveInstanceState() 之后执行。
     * - 逼不得已时使用commitAllowingStateLoss()。
     *
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        // 默认实现组件状态保存
        super.onSaveInstanceState(outState)
        LogUtil.v(tag, "$simpleName：onSaveInstanceState(Bundle)")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.e(tag, "$simpleName：onStop()")
    }

    /**
     * onStop() 之后执行
     */
    override fun onRestart() {
        super.onRestart()
        LogUtil.i(tag, "$simpleName：onRestart()")
    }

    /**
     * 执行不代表 finish() 函数也会执行
     */
    override fun onDestroy() {
        super.onDestroy()
        ToastUtils.destroyExToast()
        ToastUtils.destroyToast()
        LogUtil.e(tag, "$simpleName：onDestroy()")
    }

    /**
     * Androidmanifest 配置了 android:configuration="orientation|screenSize"，再切换屏幕时候会执行该函数，
     * 其他生命周期函数不执行
     *
     * @param newConfig
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtil.v(tag, simpleName + "：onConfigurationChanged() -- " + newConfig.orientation)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    protected fun monitorNetWork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            // 请注意这里会有一个版本适配bug，所以请在这里添加非空判断
            connectivityManager?.requestNetwork(NetworkRequest.Builder().build(), object : NetworkCallback() {
                /**
                 * 网络可用的回调
                 */
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    LogUtil.e(tag, "$simpleName：onAvailable")
                }

                /**
                 * 网络丢失的回调
                 */
                override fun onLost(network: Network) {
                    super.onLost(network)
                    LogUtil.e(tag, "$simpleName：onLost")
                }

                /**
                 * 当建立网络连接时，回调连接的属性
                 */
                override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                    super.onLinkPropertiesChanged(network, linkProperties)
                    LogUtil.e(tag, "$simpleName：onLinkPropertiesChanged")
                }

                /**
                 * 按照官方的字面意思是，当我们的网络的某个能力发生了变化回调，那么也就是说可能会回调多次
                 *
                 *
                 * 之后在仔细的研究
                 */
                override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    LogUtil.e(tag, "$simpleName：onCapabilitiesChanged")
                }

                /**
                 * 在网络失去连接的时候回调，但是如果是一个生硬的断开，他可能不回调
                 */
                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    LogUtil.e(tag, "$simpleName：onLosing")
                }

                /**
                 * 按照官方注释的解释，是指如果在超时时间内都没有找到可用的网络时进行回调
                 */
                override fun onUnavailable() {
                    super.onUnavailable()
                    LogUtil.e(tag, "$simpleName：onUnavailable")
                }
            })
        }
    }
}