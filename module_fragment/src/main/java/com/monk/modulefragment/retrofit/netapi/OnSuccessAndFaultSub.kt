package com.monk.modulefragment.retrofit.netapi

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import com.monk.commonutils.LogUtil
import com.monk.modulefragment.retrofit.netapi.CompressUtils.decompress
import io.reactivex.observers.DisposableObserver
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * @author monk
 * @date 2019-2-19
 */
class OnSuccessAndFaultSub : DisposableObserver<ResponseBody?>, ProgressCancelListener {
    private val tag = "OnSuccessAndFaultSub"
    private var showProgress = true
    private val mOnSuccessAndFaultListener: OnSuccessAndFaultListener
    private var context: Context? = null
    private var progressDialog: ProgressDialog? = null

    constructor(mOnSuccessAndFaultListener: OnSuccessAndFaultListener) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener
    }

    constructor(mOnSuccessAndFaultListener: OnSuccessAndFaultListener, context: Context?) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener
        this.context = context
        progressDialog = ProgressDialog(context)
    }

    constructor(mOnSuccessAndFaultListener: OnSuccessAndFaultListener, context: Context?, showProgress: Boolean) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener
        this.context = context
        progressDialog = ProgressDialog(context)
        this.showProgress = showProgress
    }

    private fun showProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog!!.show()
        }
    }

    private fun dismissProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog!!.dismiss()
        }
    }

    public override fun onStart() {
        showProgressDialog()
    }

    override fun onComplete() {
        dismissProgressDialog()
        progressDialog = null
    }

    override fun onError(e: Throwable) {
        try {
            if (e is SocketTimeoutException) {
            } else if (e is ConnectException) {
                mOnSuccessAndFaultListener.onFault("网络连接超时")
            } else if (e is SSLHandshakeException) {
                mOnSuccessAndFaultListener.onFault("安全证书异常")
            } else if (e is HttpException) {
                val code = e.code()
                if (code == 504) {
                    mOnSuccessAndFaultListener.onFault("网络异常，请检查您的网络状态")
                } else if (code == 404) {
                    mOnSuccessAndFaultListener.onFault("请求的地址不存在")
                } else {
                    mOnSuccessAndFaultListener.onFault("请求失败")
                }
            } else if (e is UnknownHostException) {
                mOnSuccessAndFaultListener.onFault("域名解析失败")
            } else {
                mOnSuccessAndFaultListener.onFault("error:" + e.message)
            }
        } catch (e2: Exception) {
            e2.printStackTrace()
        } finally {
            LogUtil.e(tag, "error:" + e.message)
            dismissProgressDialog()
            progressDialog = null
        }
    }

    override fun onNext(body: ResponseBody) {
        try {
            val result = decompress(body.byteStream())
            Log.e("body", result)
            val jsonObject = JSONObject(result)
            val resultCode = jsonObject.getInt("ErrorCode")
            if (resultCode == 1) {
                mOnSuccessAndFaultListener.onSuccess(result)
            } else {
                val errorMsg = jsonObject.getString("ErrorMessage")
                mOnSuccessAndFaultListener.onFault(errorMsg)
                LogUtil.e(tag, "errorMsg: $errorMsg")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCancelProgress() {
        if (!this.isDisposed) {
            dispose()
        }
    }
}