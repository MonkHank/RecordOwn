package com.monk.modulefragment.rxjava2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.monk.activity.base.BaseFragment
import com.monk.commonutils.LogUtil
import com.monk.modulefragment.R
import io.reactivex.disposables.Disposable

/**
 * @author peter
 * @date 2019-1-21 14:52:16
 */
class RxJava2Fragment : BaseFragment<RxJava2Fragment?>() {
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var subscribe: Disposable? = null
    private val subscribe1: Disposable? = null
    private val subscribe2: Disposable? = null
    private val subscribe3: Disposable? = null
    private val subscribe4: Disposable? = null

    companion object {
        private const val tag = "RxJava2Fragment"
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): RxJava2Fragment {
            val fragment = RxJava2Fragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(Companion.tag, "onCreate")
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtil.i(Companion.tag, "onCreateView\tbundle=$savedInstanceState")
        val view = inflater.inflate(R.layout.fra_modulefra_rx_java2, container, false)
        val tvRxJava2 = view.findViewById<TextView>(R.id.tvRxJava2)
        tvRxJava2.text = arguments!!.getString(ARG_PARAM1)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.i(Companion.tag, "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtil.i(Companion.tag, "onActivityCreated")
        //        TestRxjava2.getInstance().create();
        subscribe = TestRxjava2.instance?.mapOperate(mActivity)?.subscribe({ douBanMovie -> LogUtil.i(Companion.tag, "成功:$douBanMovie") }) { throwable -> LogUtil.e(Companion.tag, "失败：" + throwable.message) }
        //        subscribe1 = TestRxjava2.getInstance().concatOperation().subscribe(new Consumer<DouBanMovie>() {
//            @Override
//            public void accept(DouBanMovie douBanMovie) throws Exception {
//                LogUtil.i(tag,"成功:"+douBanMovie.toString());
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                LogUtil.e(tag,"失败："+throwable.getMessage());
//            }
//        });

//        subscribe2 = TestRxjava2.getInstance().flatMapOperation().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DouBanMovie>() {
//            @Override
//            public void accept(DouBanMovie douBanMovie) throws Exception {
//                LogUtil.i(tag,"成功:"+douBanMovie.toString());
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                LogUtil.e(tag,"失败："+throwable.getMessage());
//            }
//        });

//        subscribe3 = TestRxjava2.getInstance().zipOperation().subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                LogUtil.v(tag,"aBoolean："+aBoolean);
//            }
//        });

//        subscribe4 = TestRxjava2.getInstance().intervalOperation().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                if (aLong == 3) {
//                    subscribe4.dispose();
//                }
//                LogUtil.v(tag,"aLong = "+aLong);
//
//            }
//        });
    }

    override fun onDetach() {
        super.onDetach()
        if (subscribe != null) {
            subscribe!!.dispose()
        }
        subscribe1?.dispose()
        subscribe2?.dispose()
        subscribe3?.dispose()
        subscribe4?.dispose()
    }


}