package com.monk.modulefragment.rxjava2.imetate1

/**
 * @author monk
 * @date 2019-02-26
 */
interface Observer<T> {
    fun onComplete()
    fun onError(e: Throwable?)
    fun onNext(t: T)
}