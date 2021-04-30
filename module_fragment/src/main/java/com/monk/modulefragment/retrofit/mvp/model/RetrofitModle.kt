package com.monk.modulefragment.retrofit.mvp.model

import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * @author monk
 * @date 2019-02-19
 */
interface RetrofitModle {
    fun getData(unitGuid: String?): Observable<ResponseBody?>
}