package com.monk.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-01-28
 */
public class BaseFragment<T extends BaseFragment> extends Fragment {
    protected final String tag = BaseFragment.class.getSimpleName();

    protected OnFragmentInteractionListener mListener;
    protected FragmentActivity mActivity;
    protected BaseFragment<T> mFragment;
    protected String simpleName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FragmentActivity) context;
        mFragment = this;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        simpleName = mFragment.getClass().getSimpleName();
        LogUtil.i(tag, simpleName + "：onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.i(tag, simpleName + "：onCreate(Bundle)");
    }

    /**
     * 初始化布局函数
     *
     * @param inflater           使用 inflate(int resource, ViewGroup root, boolean attachToRoot)
     *                           时，第三个参数要指定为false，因为在Fragment内部实现中，会把该布局添加到
     *                           container中，如果设为true，那么就会重复做两次添加，则会抛异常，已经添加过；
     * @param container          Fragment 附属的 FragmentActivity 布局中的 FrameLayout
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i(tag, simpleName + "：onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 只有 onCreateView() 函数返回相对于的布局，才会执行这个函数
     *
     * @param view               onCreateView() 函数返回的 View
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(tag, simpleName + "：onViewCreated()-- " + view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i(tag, simpleName + "：onActivityCreated()");
    }

    /**
     * 生命周期函数，都会执行
     *
     * @param savedInstanceState
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LogUtil.v(tag, simpleName + "：onViewStateRestored()");
    }

    /**
     * 执行完毕，依次从 onAttach() 函数到这个函数为止，附属的 FragmentActivity 才会才执行它自己
     * 的 onStart() 函数
     */
    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i(tag, simpleName + "：onStart()");
    }

    /**
     * 附属的 FragmentActivity 执行完它自己的 onResume()，就会执行这个函数
     */
    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(tag, simpleName + "：onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e(tag, simpleName + "：onPause()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.v(tag, simpleName + "：onSaveInstanceState()");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(tag, simpleName + "：onStop()");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.e(tag, simpleName + "：onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(tag, simpleName + "：onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        LogUtil.e(tag, simpleName + "：onDetach()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.v(tag, simpleName + "：onConfigurationChanged()-- " + newConfig.orientation);
    }
}
