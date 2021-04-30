package com.monk.jni;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.monk.activity.base.BaseFragment;
import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-1-22 15:15:33
 */
public class JniFragment extends BaseFragment<JniFragment>{
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

        view.findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
        TextView tvJni = view.findViewById(R.id.tvJni);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvJni.getLayoutParams();
        lp.topMargin = 100;
        lp.leftMargin = 100;
        tvJni.setLayoutParams(lp);
        tvJni.setText(sayHello("成功调用jni"));

        return view;
    }

}
