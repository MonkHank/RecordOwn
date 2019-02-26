package com.monk.retrofit.mvp.model;

import com.monk.retrofit.netapi.HttpMethods;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author monk
 * @date 2019-02-19
 */
public class RetrofitModleImpl implements RetrofitModle {

    @Override
    public Observable<ResponseBody> getData(String unitGuid) {
        return HttpMethods.getInstance().getHttpApi().getDataForMap("", "", unitGuid);
    }
}
