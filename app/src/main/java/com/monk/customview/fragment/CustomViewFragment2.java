package com.monk.customview.fragment;


import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.monk.activity.base.BaseFragment;
import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;
import com.monk.global.Constant;
import com.peoplesafe.views.QQListView;

/**
 * @author monk
 * @date 2019-1-25 16:08:31
 */
public class CustomViewFragment2 extends BaseFragment {
    private final String tag ="CustomViewFragment2";

    private QQListView qqListView;

    public CustomViewFragment2() {
        // Required empty public constructor
    }

    public static CustomViewFragment2 newInstance() {
        CustomViewFragment2 fragment = new CustomViewFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_custom_view_fragment2, container, false);
        qqListView = view.findViewById(R.id.qqListView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] arrays = {"jack", "nancy", "monk", "peter", "tome","jerry","McGrady","Carter","YaoMing"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_list_item_1, arrays);
        qqListView.setAdapter(adapter);
        qqListView.setDelButtonClickListener(new QQListView.DelButtonClickListener() {
            @Override
            public void clickHappend(int position) {
                LogUtil.e(tag,String.valueOf(position));
            }
        });

        qqListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    String uriPath= Constant.URI_SCKEME+mActivity.getPackageName()+"/"+CustomViewFragment2.class.getName();
                    LogUtil.e(tag,uriPath);
                    mListener.onFragmentInteraction( Uri.parse(uriPath));
                }
            }
        });
    }
}
