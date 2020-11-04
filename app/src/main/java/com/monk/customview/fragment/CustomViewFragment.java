package com.monk.customview.fragment;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monk.activity.base.BaseFragment;
import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;
import com.monk.global.Constant;
import com.peoplesafe.views.PercentCircleView;

/**
 * @author monk
 * @date 2019-1-24 10:37:01
 */
public class CustomViewFragment extends BaseFragment implements View.OnClickListener{
    private final String tag = "CustomViewFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    private PercentCircleView percentCircleView;
    private AppCompatButton btNext;

    public static CustomViewFragment newInstance(String param1, String param2) {
        CustomViewFragment fragment = new CustomViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i(tag,"onCreateView");
        View view= inflater.inflate(R.layout.fragment_custom_vie, container, false);
        percentCircleView = view.findViewById(R.id.percentCircleView);
        btNext = view.findViewById(R.id.btNext);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            String uriPath= Constant.URI_SCKEME+mActivity.getPackageName()+"/"+CustomViewFragment.class.getName();
            LogUtil.e(tag,uriPath);
            mListener.onFragmentInteraction( Uri.parse(uriPath));
        }
    }
}
