package com.monk.retrofit.netapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.monk.commonutils.LogUtil;

import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @author monk
 * @date 2019-2-19
 */
public class OnSuccessAndFaultSub extends DisposableObserver<ResponseBody>
        implements ProgressCancelListener {
    private final String tag = "OnSuccessAndFaultSub";
    private boolean showProgress = true;
    private final OnSuccessAndFaultListener mOnSuccessAndFaultListener;

    private Context context;
    private ProgressDialog progressDialog;


    public OnSuccessAndFaultSub(OnSuccessAndFaultListener mOnSuccessAndFaultListener) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener;
    }

    public OnSuccessAndFaultSub(OnSuccessAndFaultListener mOnSuccessAndFaultListener, Context context) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener;
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    public OnSuccessAndFaultSub(OnSuccessAndFaultListener mOnSuccessAndFaultListener, Context context, boolean showProgress) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        this.showProgress = showProgress;
    }


    private void showProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog.show();
        }
    }


    private void dismissProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
        progressDialog = null;
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof SocketTimeoutException) {
            } else if (e instanceof ConnectException) {
                mOnSuccessAndFaultListener.onFault("网络连接超时");
            } else if (e instanceof SSLHandshakeException) {
                mOnSuccessAndFaultListener.onFault("安全证书异常");
            } else if (e instanceof HttpException) {
                int code = ((HttpException) e).code();
                if (code == 504) {
                    mOnSuccessAndFaultListener.onFault("网络异常，请检查您的网络状态");
                } else if (code == 404) {
                    mOnSuccessAndFaultListener.onFault("请求的地址不存在");
                } else {
                    mOnSuccessAndFaultListener.onFault("请求失败");
                }
            } else if (e instanceof UnknownHostException) {
                mOnSuccessAndFaultListener.onFault("域名解析失败");
            } else {
                mOnSuccessAndFaultListener.onFault("error:" + e.getMessage());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            LogUtil.e(tag, "error:" + e.getMessage());
            dismissProgressDialog();
            progressDialog = null;
        }
    }


    @Override
    public void onNext(ResponseBody body) {
        try {
            final String result = CompressUtils.decompress(body.byteStream());
            Log.e("body", result);
            JSONObject jsonObject = new JSONObject(result);
            int resultCode = jsonObject.getInt("ErrorCode");
            if (resultCode == 1) {
                mOnSuccessAndFaultListener.onSuccess(result);
            } else {
                String errorMsg = jsonObject.getString("ErrorMessage");
                mOnSuccessAndFaultListener.onFault(errorMsg);
                LogUtil.e(tag, "errorMsg: " + errorMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCancelProgress() {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }
}
