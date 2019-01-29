package com.monk.jni;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 * javah -encoding UTF-8  -classpath F:\study\AIDLDemo\app\src\main\java -jni com.monk.jni.JniFragment
 *
 * @author monk
 * @date 2019-1-22 15:15:33
 */
public class JniFragment extends Fragment {
    private final String tag="JniFragment";

    static {
        System.loadLibrary("native-lib");
    }

    public native String sayHello(String string);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LogUtil.i(tag,"\ninflater = "+inflater+"\ncontainer = "+container+"\nbundle = "+savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_jni, container, false);
        TextView tvJni = view.findViewById(R.id.tvJni);
        tvJni.setText(sayHello("咦嘿"));
        return view;
    }

}
