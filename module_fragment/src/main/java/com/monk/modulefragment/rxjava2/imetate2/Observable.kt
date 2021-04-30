package com.monk.modulefragment.rxjava2.imetate2

/**
 * @author monk
 * @date 2019-03-01
 */
abstract class Observable<T> {
    fun subscribe(observer: Observer<T>?) {
        subscribeActual(observer)
    }

    abstract fun subscribeActual(observer: Observer<T>?)

    companion object {
        fun <T> create(observableOnSubscribe: ObservableOnSubscribe<T>?): Observable<T> {
            return ObservableCreator(observableOnSubscribe!!)
        }
    }
}