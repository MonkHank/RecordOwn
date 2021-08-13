package com.monk

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.monk.activity.base.BaseCompatActivity
import com.monk.aidldemo.R


class ActFlutter : BaseCompatActivity<ActFlutter?>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.layout.act_flutter)


        //创建一个FlutterView
        val flutterView: FlutterView = Flutter.createView(this, lifecycle, "route1")
        //实例化容器
        val layout: FrameLayout = findViewById(R.id.flutter_container)
        //将FlutterView添加到容器中去
        layout.addView(flutterView)
        //解决原生页面跳转Flutter页面黑屏的问题（原理就是先让界面隐藏，等第一帧绘制完成后，再让他显示出来）
        val listeners: Array<FlutterView.FirstFrameListener?> = arrayOfNulls<FlutterView.FirstFrameListener>(1)
        listeners[0] = object : FirstFrameListener() {
            fun onFirstFrame() {
                layout.visibility = View.VISIBLE
            }
        }
        flutterView.addFirstFrameListener(listeners[0])
    }
}