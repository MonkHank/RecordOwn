package com.monk.customview.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.monk.aidldemo.R;
import com.monk.customview.QQListView;
import com.monk.utils.LogUtil;

/**
 * @author monk
 * @date 2019-1-25 16:08:31
 */
public class CustomViewFragment2 extends Fragment {
    private final String tag ="CustomViewFragment2";

    private  FragmentActivity mActivity;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (FragmentActivity) context;
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
    }
}
