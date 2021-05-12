package com.monk.activity

import android.os.Bundle
import androidx.appcompat.widget.AppCompatCheckedTextView
import butterknife.BindView
import com.monk.activity.base.BaseCompatActivity
import com.monk.aidldemo.R
import com.monk.commonutils.DeviceHelper

class DeviceActivity : BaseCompatActivity<DeviceActivity?>() {
    @BindView(R.id.textView)
    var textView: AppCompatCheckedTextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(R.layout.activity_device)
        val deviceMsg = DeviceHelper.instance.getDeviceMsg(this)
        textView!!.text = deviceMsg
    }
}