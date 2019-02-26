package com.monk.retrofit.mvp.model;


import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author monk
 * @date 2019-02-19
 */
public interface RetrofitModle {

    Observable<ResponseBody> getData(String unitGuid);

}
