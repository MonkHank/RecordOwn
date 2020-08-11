package com.monk.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckedTextView;

import com.monk.aidldemo.R;
import com.monk.base.BaseCompatActivity;
import com.monk.commonutils.DeviceHelper;

import butterknife.BindView;

public class DeviceActivity extends BaseCompatActivity {

    @BindView(R.id.textView) AppCompatCheckedTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.activity_device);

        String deviceMsg = DeviceHelper.getInstance().getDeviceMsg(this);

        textView.setText(deviceMsg);
    }
}
