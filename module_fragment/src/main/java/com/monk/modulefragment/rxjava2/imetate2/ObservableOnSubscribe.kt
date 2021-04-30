package com.monk.modulefragment.rxjava2.imetate2

/**
 * @author monk
 * @date 2019-03-01
 */
interface ObservableOnSubscribe<T> {
    fun subscribe(emitter: ObservableCreator.ObservableEmitter<T>?)
}