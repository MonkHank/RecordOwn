package com.monk.modulefragment.retrofit.mvp.model

import com.monk.modulefragment.retrofit.netapi.HttpMethods
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * @author monk
 * @date 2019-02-19
 */
class RetrofitModleImpl : RetrofitModle {
    override fun getData(unitGuid: String?): Observable<ResponseBody?> {
        return HttpMethods.getInstance().httpApi.getDataForMap("", "", unitGuid)!!
    }
}