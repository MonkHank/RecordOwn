package com.monk.imageloader;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * @author JackieHank
 * @date 2017-08-19 13:08.
 */
public enum  ProgressbarUtils {
    /***/
    INSTANCE;

    private ProgressDialog dialogProgress;
    private Activity mActivity;

    public void showProgressBar(Activity mContext, String text) {
        if (mContext != mActivity) {
            dialogProgress = null;
            mActivity = mContext;
        }
        if (dialogProgress == null) {
            dialogProgress = new ProgressDialog(mContext);
            dialogProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialogProgress.setCancelable(true);
            dialogProgress.setCanceledOnTouchOutside(false);
        }
        dialogProgress.setMessage(text);
        if (mContext != null && !mContext.isFinishing()) {
            dialogProgress.show();
        }
    }

    public void hideProgressBar(Activity mContext) {
        boolean condition = mContext != null && !mContext.isDestroyed() && !mContext.isFinishing();
        if (condition && dialogProgress != null && dialogProgress.isShowing()) {
            dialogProgress.dismiss();
        }
    }

    public void existApp() {
        if (mActivity != null) {
            mActivity =null;
        }
    }
}
