package com.monk.retrofit.netapi;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * @author monk
 * @date 2019-02-19
 */
public interface HttpApi {
    /**
     *  http://223.113.1.208:63080/AppWebApi/api/employee/xx/coiper/list
     * @param accessCode
     * @param sign
     * @param unitGuid
     * @return
     */
    @GET("employee/{unitGuid}/coiper/list")
    Observable<ResponseBody>getDataForMap(@Header("AccessCode") String accessCode, @Header("Sign") String sign, @Path("unitGuid") String unitGuid);
}
