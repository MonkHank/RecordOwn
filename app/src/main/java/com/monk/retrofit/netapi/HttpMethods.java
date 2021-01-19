package com.monk.retrofit.netapi;


import androidx.annotation.NonNull;

import com.monk.commonutils.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author monk
 * @date 2019-2-19 09:58:38
 */
public class HttpMethods {
    public String TAG = "HttpMethods";
    public static String BASE_URL = "http://223.113.1.208:63080/AppWebApi/api/";
    private static final int DEFAULT_CONNECT_TIMEOUT = 30;
    private static final int DEFAULT_WRITE_TIMEOUT = 30;
    private static final int DEFAULT_READ_TIMEOUT = 30;
    private Retrofit retrofit;
    private HttpApi httpApi;

    /*** 请求失败重连次数*/
    private final int RETRY_COUNT = 0;
    private final OkHttpClient.Builder okHttpBuilder;

    private HttpMethods() {
        okHttpBuilder = new OkHttpClient.Builder();
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                response.newBuilder()
                        .build();
                return response;
            }
        };
        okHttpBuilder.addInterceptor(cacheInterceptor);

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .addHeader("Accept", "application/json; charset=utf-8")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        okHttpBuilder.addInterceptor(headerInterceptor);

//        if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                LogUtil.v(TAG,message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置 Debug Log 模式
        okHttpBuilder.addInterceptor(loggingInterceptor);


        okHttpBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        //错误重连
        okHttpBuilder.retryOnConnectionFailure(true);

        retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        httpApi = retrofit.create(HttpApi.class);
    }


    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();

    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void changeBaseUrl(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        httpApi = retrofit.create(HttpApi.class);
    }

    public HttpApi getHttpApi() {
        return httpApi;
    }

    public <T> void toSubscribe(Observable<T> o, DisposableObserver<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)
                .subscribe(s);
    }
}