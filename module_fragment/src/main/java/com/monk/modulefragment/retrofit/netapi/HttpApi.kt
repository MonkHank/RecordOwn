package com.monk.modulefragment.retrofit.netapi

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * @author monk
 * @date 2019-02-19
 */
interface HttpApi {
    /**
     * http://223.113.1.208:63080/AppWebApi/api/employee/xx/coiper/list
     * @param accessCode
     * @param sign
     * @param unitGuid
     * @return
     */
    @GET("employee/{unitGuid}/coiper/list")
    fun getDataForMap(@Header("AccessCode") accessCode: String?, @Header("Sign") sign: String?, @Path("unitGuid") unitGuid: String?): Observable<ResponseBody?>?
}