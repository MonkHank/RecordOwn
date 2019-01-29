package com.monk.rxjava2;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.monk.commonutils.LogUtil;
import com.monk.global.Constant;
import com.monk.utils.FileCacheUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author monk
 * @date 2019-01-03
 */
public class TestRxjava2 {
    private final String tag = "TestRxjava2";
    private static volatile TestRxjava2 rxjava2;

    private TestRxjava2() {
    }

    public static TestRxjava2 getInstance() {
        if (rxjava2 == null) {
            rxjava2 = new TestRxjava2();
        }
        return rxjava2;
    }

    public void create() {
        // 第一步：初始化Observable
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                LogUtil.e(tag, "Observable thread is：" + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.e(tag, "After observeOn(mainThread),Current thread is：" + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                // 第三步：订阅
                .subscribe(new Observer<Integer>() {
                    // 第二步：初始化observer
                    private int i;
                    private Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.v(tag, "onNext :" + integer);
                        LogUtil.e(tag, "After observeOn(io),Current thread is：" + Thread.currentThread().getName());
                        mDisposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(tag, "onError : value : " + e.getMessage() + "\n");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e(tag, "onComplete" + "\n");
                    }
                });

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//
//            }
//        }).flatMap(new Function<String, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(String s) throws Exception {
//                return null;
//            }
//        }).map(new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) throws Exception {
//                return null;
//            }
//        });
    }

    public Observable<DouBanMovie> mapOperate() {
        return Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> e) throws Exception {
                Request request = new Request.Builder()
                        .url(Constant.GET_DOUBAN_URL)
                        .get().build();
                Call call = new OkHttpClient().newCall(request);
                Response response = call.execute();
                e.onNext(response);
            }
        }).map(new Function<Response, DouBanMovie>() {
            @Override
            public DouBanMovie apply(Response response) throws Exception {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String string = body.string();
                        LogUtil.i(tag, string, false);
                        FileCacheUtils.setCache(tag, string);
                        return new Gson().fromJson(string, DouBanMovie.class);
                    }
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<DouBanMovie>() {
                    @Override
                    public void accept(DouBanMovie douBanMovie) throws Exception {
                        LogUtil.e(tag, "doNext：保存成功：" + douBanMovie.toString() + "\n");
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DouBanMovie> concatOperation() {
        return Observable.concat(Observable.create(new ObservableOnSubscribe<DouBanMovie>() {
            @Override
            public void subscribe(ObservableEmitter<DouBanMovie> e) throws Exception {
                LogUtil.e(tag, "create 当前线程：" + Thread.currentThread().getName());
                String cache = FileCacheUtils.getCache(tag);
                if (!"".equals(cache)) {
                    LogUtil.v(tag, "读缓存数据");
                    DouBanMovie douBanMovie = JSON.parseObject(cache, DouBanMovie.class);
                    e.onNext(douBanMovie);
                } else {
                    LogUtil.i(tag, "读取网络数据");
                    e.onComplete();
                }
            }
        }), mapOperate())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DouBanMovie> flatMapOperation() {
        return Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> e) throws Exception {
                Request request = new Request.Builder()
                        .url(Constant.GET_DOUBAN_URL)
                        .get().build();
                Call call = new OkHttpClient().newCall(request);
                Response response = call.execute();
                e.onNext(response);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Response>() {
            @Override
            public void accept(Response response) throws Exception {
                // 指定订阅者接收事件在 mainThread 处理获取到的网络请求数据
            }
        }).observeOn(Schedulers.io()).flatMap(new Function<Response, ObservableSource<DouBanMovie>>() {
            @Override
            public ObservableSource<DouBanMovie> apply(final Response response) throws Exception {
                // 指定订阅者接收事件在 io 线程
                return Observable.create(new ObservableOnSubscribe<DouBanMovie>() {
                    @Override
                    public void subscribe(ObservableEmitter<DouBanMovie> e) throws Exception {
                        ResponseBody body = response.body();
                        DouBanMovie douBanMovie = null;
                        if (body != null) {
                            String json = body.string();
                            douBanMovie = JSON.parseObject(json, DouBanMovie.class);
                            LogUtil.i(tag, douBanMovie.toString());
                        }
                        e.onNext(douBanMovie);
                    }
                });
            }
        });
    }

    public Observable<Boolean> zipOperation() {
        return Observable.zip(Observable.just("1"), Observable.just(1), new BiFunction<String, Integer, Boolean>() {
            @Override
            public Boolean apply(String s, Integer integer) throws Exception {
                int result = Integer.parseInt(s) + integer;
                return result==2;

            }
        });
    }

    public Observable<Long> intervalOperation(){
        return Observable.interval(1,TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                LogUtil.e(tag,"aLone："+aLong);
            }
        });
    }
}
