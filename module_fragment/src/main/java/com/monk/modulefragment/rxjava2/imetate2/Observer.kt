package com.monk.modulefragment.rxjava2.imetate2

/**
 * @author monk
 * @date 2019-03-01
 */
interface Observer<T> {
    fun onSubscribe(disposable: Disposable?)
    fun onNext(t: T)
    fun onCompleted()
    fun onError(throwable: Throwable?)
}