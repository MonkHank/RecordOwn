package com.monk.modulefragment.retrofit.mvp.present

import com.google.gson.Gson
import com.monk.commonutils.LogUtil
import com.monk.global.Constant
import com.monk.modulefragment.retrofit.base.BasePresent
import com.monk.modulefragment.retrofit.bean.Bean
import com.monk.modulefragment.retrofit.mvp.model.RetrofitModle
import com.monk.modulefragment.retrofit.mvp.model.RetrofitModleImpl
import com.monk.modulefragment.retrofit.mvp.view.RetrofitView
import com.monk.modulefragment.retrofit.netapi.OnSuccessAndFaultListener
import com.monk.modulefragment.retrofit.netapi.OnSuccessAndFaultSub
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author monk
 * @date 2019-02-19
 */
class RetrofitPresentImpl(mMvpView: RetrofitView?) : BasePresent<RetrofitView?>(mMvpView), RetrofitPresent {
    private val tag = "RetrofitPresentImpl"
    private val retrofitModle: RetrofitModle
    override fun getCopyList(unitGuid: String?) {
        val accessCode = "b938b3ee-2f5e-4c77-94bd-dc94789dd18a"
        val headerEncryption = ("accesscode=" + accessCode
                + "&apibody=" + ""
                + "&apiurl=" + "/AppWebApi/api/employee/00000000-0000-0002-facc-000000001824/coiper/list"
                + "&timestamp=" + System.currentTimeMillis() / 1000 + "&webapikey=" + Constant.WEB_API_KEY)
        //        String md5 = MD5Utils.getMD5(headerEncryption);
        val md5 = ""
        mvpView!!.showProgressBar()
        retrofitModle.getData(unitGuid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(OnSuccessAndFaultSub(object : OnSuccessAndFaultListener {
                    override fun onSuccess(result: String?) {
                        LogUtil.i(tag, result)
                        val bean = Gson().fromJson(result, Bean::class.java)
                        mvpView!!.displayBean(bean)
                        mvpView!!.dismissProgressBar()
                    }

                    override fun onFault(errorMsg: String?) {
                        LogUtil.e(tag, errorMsg)
                        mvpView!!.showError(errorMsg)
                        mvpView!!.dismissProgressBar()
                    }
                }))
    }

    init {
        retrofitModle = RetrofitModleImpl()
    }
}