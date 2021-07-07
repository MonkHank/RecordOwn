package com.monk.moduleviews.fragments

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.monk.moduleviews.ViewsDetailActivity
import com.monk.activity.base.BaseFragment
import com.monk.commonutils.LogUtil
import com.monk.moduleviews.R

class FraViewLayoutInflater : BaseFragment<FraViewLayoutInflater?>(){

    private var button: Button? = null
    private var tvMsg: TextView? = null

    private val sb = StringBuilder()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fra_moduleviews_layoutinflater, container, false)
        tvMsg = rootView.findViewById(R.id.tvMsg)

        val constraintLayout: LinearLayout = rootView.findViewById(R.id.linearLayout)
        val view = inflater.inflate(R.layout.content_scrolling, constraintLayout, true)
        button = view.findViewById(R.id.button)
//        linearLayout.addView(view);
//        linearLayout.addView(view);

        LogUtil.v(Tags, "相对parent的高度：" + button?.top)
        sb.append("onCreate()-相对parent的高度：${button?.top}\n")
        tvMsg?.text=sb.toString()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(mActivity, simpleName)
                .setContentTitle("contentTitle")
                .setContentText("contentText")
                .setContentIntent(PendingIntent.getActivity(mActivity, 0, Intent(mActivity, ViewsDetailActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT))
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()
        notificationManager.notify(0, notification)


    }

    fun onWindowFocusChanged(hasFocus: Boolean){
        LogUtil.v(Tags, "onWindowFocusChanged()-相对parent的高度：${button?.top}")
        sb.append("onWindowFocusChanged()-相对parent的高度：${button?.top}\n")
        tvMsg?.text=sb.toString()
    }



}