package com.monk.commonutils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * @author Administrator
 * @date 2017/8/2/002.
 */

public class ToastUtils {
    private static Toast mToast;
    private static Toast mToast2;
    private static TextView mTvToast;

    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public static void showImageToast(Context context, String text) {
        if (mToast2 == null) {
            mToast2 = new Toast(context);
            mToast2.setDuration(LENGTH_SHORT);
            mToast2.setGravity(Gravity.CENTER, 0, 0);
            View view = LayoutInflater.from(context).inflate(R.layout.toast_layout,null);
            mTvToast = view.findViewById(R.id.tv_save_contact);
            mToast2.setView(view);
        }
        mTvToast.setText(text);
        mToast2.show();
    }


    public static void destroyExToast() {
        if (mToast2 != null) {
            mToast2 = null;
            mTvToast = null;
        }
    }

    public static void destroyToast() {
        if (mToast != null) {
            mToast = null;
        }
    }
}
