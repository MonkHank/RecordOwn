package com.monk.activity.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.monk.commonutils.LogUtil

/**
 * @author monk
 * @date 2019-01-28
 */
open class BaseFragment<T : BaseFragment<T>?> : Fragment() {
    protected var Tags: String? = null
    protected var mListener: OnFragmentInteractionListener? = null
    protected lateinit var mActivity: FragmentActivity
    protected var mFragment: BaseFragment<T>? = null
    protected lateinit var simpleName: String
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
        mFragment = this
        if (context is OnFragmentInteractionListener) {
            mListener = context
        }
        simpleName = mFragment!!.javaClass.simpleName
        Tags = simpleName
        LogUtil.i(Tags, "$simpleName：onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(Tags, "$simpleName：onCreate(Bundle)")
    }

    /**
     * 初始化布局函数
     *
     * @param inflater           使用 inflate(int resource, ViewGroup root, boolean attachToRoot)
     * 时，第三个参数要指定为false，因为在Fragment内部实现中，会把该布局添加到
     * container中，如果设为true，那么就会重复做两次添加，则会抛异常，已经添加过；
     * @param container          Fragment 附属的 FragmentActivity 布局中的 FrameLayout
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtil.i(Tags, "$simpleName：onCreateView()")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * 只有 onCreateView() 函数返回相对于的布局，才会执行这个函数
     *
     * @param view               onCreateView() 函数返回的 View
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.i(Tags, "$simpleName：onViewCreated()-- $view")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtil.i(Tags, "$simpleName：onActivityCreated()")
    }

    /**
     * 生命周期函数，都会执行
     *
     * @param savedInstanceState
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        LogUtil.v(Tags, "$simpleName：onViewStateRestored()")
    }

    /**
     * 执行完毕，依次从 onAttach() 函数到这个函数为止，附属的 FragmentActivity 才会才执行它自己
     * 的 onStart() 函数
     */
    override fun onStart() {
        super.onStart()
        LogUtil.i(Tags, "$simpleName：onStart()")
    }

    /**
     * 附属的 FragmentActivity 执行完它自己的 onResume()，就会执行这个函数
     */
    override fun onResume() {
        super.onResume()
        LogUtil.i(Tags, "$simpleName：onResume()")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.e(Tags, "$simpleName：onPause()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.v(Tags, "$simpleName：onSaveInstanceState()")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.e(Tags, "$simpleName：onStop()")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.e(Tags, "$simpleName：onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.e(Tags, "$simpleName：onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        LogUtil.e(Tags, "$simpleName：onDetach()")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtil.v(Tags, simpleName + "：onConfigurationChanged()-- " + newConfig.orientation)
    }
}