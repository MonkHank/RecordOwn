package com.monk.jni;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monk.aidldemo.R;
import com.monk.base.BaseFragment;
import com.monk.commonutils.LogUtil;
import com.monk.retrofit.mvp.view.RetrofitActivity;

/**
 * @author monk
 * @date 2019-1-22 15:15:33
 */
public class JniFragment extends BaseFragment implements View.OnClickListener{
    private final String tag="JniFragment";

    static {
        System.loadLibrary("native-lib");
    }

    public native String sayHello(String string);


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LogUtil.i(tag,"\ninflater = "+inflater+"\ncontainer = "+container+"\nbundle = "+savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_jni, container, false);
        TextView tvJni = view.findViewById(R.id.tvJni);
        tvJni.setText(sayHello("成功调用jni"));
        view.findViewById(R.id.btNext).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(mActivity,RetrofitActivity.class));
    }
}
