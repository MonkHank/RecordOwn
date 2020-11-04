package com.monk.rxjava2;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monk.activity.base.BaseFragment;
import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;
import com.monk.global.Constant;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author peter
 * @date 2019-1-21 14:52:16
 */
public class RxJava2Fragment extends BaseFragment implements View.OnClickListener{
    private static final String tag = "RxJava2Fragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Disposable subscribe,subscribe1,subscribe2,subscribe3, subscribe4;


    public static RxJava2Fragment newInstance(String param1, String param2) {
        RxJava2Fragment fragment = new RxJava2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(tag,"onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i(tag,"onCreateView"+"\tbundle="+savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_rx_java2, container, false);
        TextView tvRxJava2 = view.findViewById(R.id.tvRxJava2);
        tvRxJava2.setText(getArguments().getString(ARG_PARAM1));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(tag,"onViewCreated");
        view.findViewById(R.id.locationButton).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i(tag,"onActivityCreated");
//        TestRxjava2.getInstance().create();

        subscribe = TestRxjava2.getInstance().mapOperate(mActivity).subscribe(new Consumer<DouBanMovie>() {
            @Override
            public void accept(DouBanMovie douBanMovie) throws Exception {
                LogUtil.i(tag, "成功:" + douBanMovie.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtil.e(tag, "失败：" + throwable.getMessage());
            }
        });
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


    @Override
    public void onDetach() {
        super.onDetach();
        if (subscribe != null) {
            subscribe.dispose();
        }
        if (subscribe1 != null) {
            subscribe1.dispose();
        }
        if (subscribe2 != null) {
            subscribe2.dispose();
        }
        if (subscribe3 != null) {
            subscribe3.dispose();
        }
        if (subscribe4 != null) {
            subscribe4.dispose();
        }
    }

    @Override
    public void onClick(View v) {
        String uriPath= Constant.URI_SCKEME+mActivity.getPackageName()+"/"+ RxJava2Fragment.class.getName();
        LogUtil.e(tag,uriPath);
        mListener.onFragmentInteraction( Uri.parse(uriPath));
    }
}
