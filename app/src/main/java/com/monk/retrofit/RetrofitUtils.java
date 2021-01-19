package com.monk.retrofit;

import com.monk.commonutils.LogUtil;
import com.monk.global.Constant;
import com.monk.rxjava2.DouBanMovie;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author monk
 * @date 2019-01-21
 */
public class RetrofitUtils {
    private static final String tag = "RetrofitUtils";
    private static final boolean debug = true;
    private Call<DouBanMovie> call;

    private static class Inner {
        private static final RetrofitUtils INSTANCE = new RetrofitUtils();
    }

    public static RetrofitUtils getInstance() {
        return Inner.INSTANCE;
    }

    public void  create()  {
        Retrofit retrofit =  new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                // IllegalArgumentException baseUrl must end in /,
                .baseUrl(Constant.BASE_URL)
                .build();

        NewService newService = retrofit.create(NewService.class);
        call = newService.getDouBan(0, 1);
    }

    /**
     * android.os.NetworkOnMainThreadException
     */
    public void syncFun() {
        // 同步
        Response<DouBanMovie> response ;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (response.isSuccessful()) {
            DouBanMovie body = response.body();
            if (body != null) {
                LogUtil.i(tag, body.toString(), debug);
            }
        }
    }

    public void asyncFun() {
        // 异步
        call.enqueue(new Callback<DouBanMovie>() {
            @Override
            public void onResponse(Call<DouBanMovie> call, Response<DouBanMovie> response) {
                if (response.isSuccessful()) {
                    DouBanMovie body = response.body();
                    if (body != null) {
                        LogUtil.i(tag, body.toString(), debug);
                    }
                }
            }

            @Override
            public void onFailure(Call<DouBanMovie> call, Throwable t) {
               LogUtil.e(tag,t.getMessage());
            }
        });
    }

    public void cancle() {
        // 取消
        call.cancel();
        if (call.isCanceled()) {
            call = null;
        }
    }
}
