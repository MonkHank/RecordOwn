package com.monk.activity;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatCheckedTextView;

import com.monk.activity.base.BaseCompatActivity;
import com.monk.aidldemo.R;
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
