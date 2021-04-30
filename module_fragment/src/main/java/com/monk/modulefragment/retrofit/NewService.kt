package com.monk.modulefragment.retrofit

import com.monk.modulefragment.rxjava2.DouBanMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author monk
 * @date 2019-01-21
 */
interface NewService {
    /**
     * http://api.douban.com/v2/movie/top250?start=0&count=1
     * java.lang.IllegalArgumentException: URL query string "start={start}&count={count}"
     * must not have replace block. For dynamic query parameters use @Query.
     * @param start
     * @param count
     * @return
     */
    //    @GET("top250?start={start}&count={count}")
    //    Call<DouBanMovie> getDouBan(@Path("start") int start, @Path("count") int count);
    @GET("top250")
    fun getDouBan(@Query("start") start: Int, @Query("count") count: Int): Call<DouBanMovie?>?
}