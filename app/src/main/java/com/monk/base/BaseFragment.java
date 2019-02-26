package com.monk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-01-28
 */
public class BaseFragment extends Fragment {
    protected FragmentActivity mActivity;
    protected OnFragmentInteractionListener mListener;
    private final String tag = "Fragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (FragmentActivity) context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        LogUtil.i(tag,"onAttach");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(tag,"onCreate");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(tag,"onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i(tag,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i(tag,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(tag,"onResume");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.i(tag,"onSaveInstanceState");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.i(tag,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.i(tag,"onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        LogUtil.i(tag,"onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.i(tag,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(tag,"onDestroy");
    }
}
