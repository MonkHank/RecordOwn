package com.monk.customview.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monk.aidldemo.R;
import com.monk.customview.PercentCircleView;
import com.monk.global.Constant;
import com.monk.utils.LogUtil;

/**
 * @author monk
 * @date 2019-1-24 10:37:01
 */
public class CustomViewFragment extends Fragment implements View.OnClickListener{
    private final String tag = "CustomViewFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private PercentCircleView percentCircleView;
    private AppCompatButton btNext;
    private FragmentActivity mActivity;

    public CustomViewFragment() {
        // Required empty public constructor
    }

    public static CustomViewFragment newInstance(String param1, String param2) {
        CustomViewFragment fragment = new CustomViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (FragmentActivity) context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
