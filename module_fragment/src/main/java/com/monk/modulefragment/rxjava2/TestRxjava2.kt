package com.monk.modulefragment.rxjava2

import android.content.Context
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.monk.commonutils.FileCacheUtils.getCache
import com.monk.commonutils.FileCacheUtils.setCache
import com.monk.commonutils.LogUtil
import com.monk.global.Constant
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import java.util.concurrent.TimeUnit

/**
 * @author monk
 * @date 2019-01-03
 */
class TestRxjava2 private constructor() {
    private val tag = "TestRxjava2"

    companion object {
        @Volatile
        private var rxjava2: TestRxjava2? = null
        val instance: TestRxjava2?
            get() {
                if (rxjava2 == null) {
                    rxjava2 = TestRxjava2()
                }
                return rxjava2
            }
    }

    fun create() {
        // 第一步：初始化Observable
        Observable.create<Int> { emitter ->
            LogUtil.e(tag, "Observable thread is：" + Thread.currentThread().name)
            emitter.onNext(1)
            emitter.onComplete()
        }.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { LogUtil.e(tag, "After observeOn(mainThread),Current thread is：" + Thread.currentThread().name) }
                .observeOn(Schedulers.io()) // 第三步：订阅
                .subscribe(object : Observer<Int> {
                    // 第二步：初始化observer
                    private val i = 0
                    private var mDisposable: Disposable? = null
                    override fun onSubscribe(d: Disposable) {
                        mDisposable = d
                    }

                    override fun onNext(integer: Int) {
                        LogUtil.v(tag, "onNext :$integer")
                        LogUtil.e(tag, "After observeOn(io),Current thread is：" + Thread.currentThread().name)
                        mDisposable!!.dispose()
                    }

                    override fun onError(e: Throwable) {
                        LogUtil.e(tag, "onError : value : " + e.message + "\n")
                    }

                    override fun onComplete() {
                        LogUtil.e(tag, "onComplete" + "\n")
                    }
                })

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

    fun mapOperate(context: Context?): Observable<DouBanMovie> {
        return Observable.create(ObservableOnSubscribe { e: ObservableEmitter<Response?> ->
            LogUtil.i(tag, "验证 execute 阻塞")
            val request: Request = Request.Builder()
                    .url(Constant.GET_DOUBAN_URL)
                    .get().build()
            val call: Call = OkHttpClient().newCall(request)
            val response: Response = call.execute()
            e.onNext(response)
            LogUtil.i(tag, response.toString())
        }).map<DouBanMovie>(Function { response ->
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val string = body.string()
                    LogUtil.i(tag, string, false)
                    LogUtil.i(tag, "body")
                    setCache((context)!!, tag, string)
                    return@Function Gson().fromJson(string, DouBanMovie::class.java)
                }
            }
            null
        }).doOnNext(Consumer { douBanMovie -> LogUtil.e(tag, "doNext：保存成功：$douBanMovie\n") } // 指定subscribe()时发生的线程
        ) // 指定被观察者 生产事件的线程
                .subscribeOn(Schedulers.io()) // 指定下游Observer回调发生的线程
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun concatOperation(context: Context?): Observable<DouBanMovie> {
        return Observable.concat(Observable.create { e ->
            LogUtil.e(tag, "create 当前线程：" + Thread.currentThread().name)
            val cache = getCache((context)!!, tag)
            if ("" != cache) {
                LogUtil.v(tag, "读缓存数据")
                val douBanMovie = JSON.parseObject(cache, DouBanMovie::class.java)
                e.onNext(douBanMovie)
            } else {
                LogUtil.i(tag, "读取网络数据")
                e.onComplete()
            }
        }, mapOperate(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun flatMapOperation(): Observable<DouBanMovie> {
        return Observable.create(ObservableOnSubscribe<Response?> { e ->
            val request = Request.Builder()
                    .url(Constant.GET_DOUBAN_URL)
                    .get().build()
            val call = OkHttpClient().newCall(request)
            val response = call.execute()
            e.onNext(response)
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext {
            // 指定订阅者接收事件在 mainThread 处理获取到的网络请求数据
        }.observeOn(Schedulers.io()).flatMap { response -> // 指定订阅者接收事件在 io 线程
            Observable.create { e ->
                val body = response.body()
                var douBanMovie: DouBanMovie? = null
                if (body != null) {
                    val json = body.string()
                    douBanMovie = JSON.parseObject(json, DouBanMovie::class.java)
                    LogUtil.i(tag, douBanMovie.toString())
                }
                e.onNext((douBanMovie)!!)
            }
        }
    }

    fun zipOperation(): Observable<Boolean> {
        return Observable.zip(Observable.just("1"), Observable.just(1), { s, integer ->
            val result = s.toInt() + integer
            result == 2
        })
    }

    fun intervalOperation(): Observable<Long> {
        return Observable.interval(1, TimeUnit.SECONDS).doOnNext { aLong -> LogUtil.e(tag, "aLone：$aLong") }
    }


}