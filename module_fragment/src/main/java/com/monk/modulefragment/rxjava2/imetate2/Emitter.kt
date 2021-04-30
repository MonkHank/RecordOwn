package com.monk.modulefragment.rxjava2.imetate2

/**
 * 发射数据
 * @author monk
 * @date 2019-03-01
 */
interface Emitter<T> {
    fun onNext(t: T)
    fun onCompleted()
    fun onError(throwable: Throwable?)
}