package com.monk.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monk.commonutils.LogUtil;

/**
 * @author monk
 * @date 2019-01-09
 */
public class TestDialogFragment extends DialogFragment {
    private final String tag = "TestDialogFragment";

    private AlertDialog alertDialog;

    public static TestDialogFragment newInstance() {
        return new TestDialogFragment();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        LogUtil.e(tag, "dismiss");
    }

    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
        LogUtil.e(tag, "dismissAllowingStateLoss");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.i(tag, "dismissAllowingStateLoss");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e(tag, "onDetach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(tag, "onCreate");
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        LogUtil.v(tag, "onCancel");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.v(tag, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.v(tag, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.v(tag, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(tag, "onDestroy");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("Hello");
        LogUtil.i(tag, "onCreateView");
        return textView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Test")
                .setMessage("Message")
                .create();
        LogUtil.i(tag, "onCreateDialog");
        return alertDialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.e(tag, "onDestroyView");
    }
}
