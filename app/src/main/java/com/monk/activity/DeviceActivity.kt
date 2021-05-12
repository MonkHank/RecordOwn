package com.monk.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckedTextView
import butterknife.BindView
import com.monk.activity.base.BaseCompatActivity
import com.monk.aidldemo.R
import com.monk.commonutils.DeviceHelper

class DeviceActivity : BaseCompatActivity<DeviceActivity?>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.layout.activity_device)
        val deviceMsg = DeviceHelper.instance.getDeviceMsg(this)
        findViewById<TextView>(R.id.textView).text = deviceMsg
    }
}