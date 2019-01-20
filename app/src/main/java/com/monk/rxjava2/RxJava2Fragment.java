package com.monk.rxjava2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monk.LogUtil;
import com.monk.aidldemo.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RxJava2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RxJava2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RxJava2Fragment extends Fragment {
    private static final String tag = "RxJava2Fragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Disposable subscribe,subscribe1,subscribe2,subscribe3, subscribe4;

    public RxJava2Fragment() {
        // Required empty public constructor
    }

    public static RxJava2Fragment newInstance(String param1, String param2) {
        RxJava2Fragment fragment = new RxJava2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.i(tag,"onAttach");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtil.i(tag,"onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rx_java2, container, false);
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
        TestRxjava2.getInstance().create();
        subscribe = TestRxjava2.getInstance().mapOperate().subscribe(new Consumer<DouBanMovie>() {
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
        subscribe1 = TestRxjava2.getInstance().concatOperation().subscribe(new Consumer<DouBanMovie>() {
            @Override
            public void accept(DouBanMovie douBanMovie) throws Exception {
                LogUtil.i(tag,"成功:"+douBanMovie.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtil.e(tag,"失败："+throwable.getMessage());
            }
        });

        subscribe2 = TestRxjava2.getInstance().flatMapOperation().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DouBanMovie>() {
            @Override
            public void accept(DouBanMovie douBanMovie) throws Exception {
                LogUtil.i(tag,"成功:"+douBanMovie.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtil.e(tag,"失败："+throwable.getMessage());
            }
        });

        subscribe3 = TestRxjava2.getInstance().zipOperation().subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                LogUtil.v(tag,"aBoolean："+aBoolean);
            }
        });

        subscribe4 = TestRxjava2.getInstance().intervalOperation().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (aLong == 3) {
                    subscribe4.dispose();
                }
                LogUtil.v(tag,"aLong = "+aLong);

            }
        });

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
