package com.monk.retrofit.mvp.present;

import com.google.gson.Gson;
import com.monk.commonutils.LogUtil;
import com.monk.global.Constant;
import com.monk.retrofit.base.BasePresent;
import com.monk.retrofit.bean.Bean;
import com.monk.retrofit.mvp.model.RetrofitModle;
import com.monk.retrofit.mvp.model.RetrofitModleImpl;
import com.monk.retrofit.mvp.view.RetrofitView;
import com.monk.retrofit.netapi.OnSuccessAndFaultListener;
import com.monk.retrofit.netapi.OnSuccessAndFaultSub;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author monk
 * @date 2019-02-19
 */
public class RetrofitPresentImpl extends BasePresent<RetrofitView> implements RetrofitPresent {
    private final String tag = "RetrofitPresentImpl";

    private final RetrofitModle retrofitModle;

    public RetrofitPresentImpl(RetrofitView mMvpView) {
        super(mMvpView);
        retrofitModle = new RetrofitModleImpl();
    }

    @Override
    public void getCopyList(String unitGuid) {
        String accessCode = "b938b3ee-2f5e-4c77-94bd-dc94789dd18a";
        String headerEncryption = "accesscode=" + accessCode
                + "&apibody=" + ""
                + "&apiurl=" + "/AppWebApi/api/employee/00000000-0000-0002-facc-000000001824/coiper/list"
                + "&timestamp=" + System.currentTimeMillis() / 1000
                + "&webapikey=" + Constant.WEB_API_KEY;
//        String md5 = MD5Utils.getMD5(headerEncryption);
        String md5 = "";
        getMvpView().showProgressBar();
        retrofitModle.getData(unitGuid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.i(tag, result);
                        Bean bean = new Gson().fromJson(result, Bean.class);
                        getMvpView().displayBean(bean);
                        getMvpView().dismissProgressBar();
                    }

                    @Override
                    public void onFault(String errorMsg) {
                        LogUtil.e(tag, errorMsg);
                        getMvpView().showError(errorMsg);
                        getMvpView().dismissProgressBar();
                    }
                }));
    }
}
