package com.monk.modulefragment.rxjava2.imetate1

import com.monk.modulefragment.rxjava2.imetate1.Observable.Companion.create
import com.monk.modulefragment.rxjava2.imetate1.Observable.OnSubscriber

/**
 * @author monk
 * @date 2019-02-26
 */
object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        create<String>(object : OnSubscriber<String> {
            override fun call(t: Subscriber<String>?) {
                t?.onNext("test")
                t?.onComplete()
            }// 这个 subscriber 就是上面这个 subscriber，

            // 响应式编程，通过 subscriber 调用其自身 onNext、onComplete方法，然后执行该些方法
        }).subscribe(object : Subscriber<String>() {
            override fun onComplete() {
                println("onComplete")
            }
            override fun onError(e: Throwable?) {}
            override fun onNext(o: String) {
                println(o)
            }
        })
    }
}