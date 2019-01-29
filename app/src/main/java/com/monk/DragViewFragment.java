package com.monk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monk.aidldemo.R;
import com.monk.global.BaseFragment;

/**
 * @author monk
 * @date 2019-1-24 10:37:01
 */
public class DragViewFragment extends BaseFragment {
    private final String tag = "DragViewFragment";



    public static DragViewFragment newInstance() {
        DragViewFragment fragment = new DragViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_drag_view, container, false);
        return view;
    }
}
