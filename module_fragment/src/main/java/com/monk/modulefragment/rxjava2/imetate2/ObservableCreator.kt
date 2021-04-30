package com.monk.modulefragment.rxjava2.imetate2

import java.util.concurrent.atomic.AtomicReference

/**
 * @author monk
 * @date 2019-03-01
 */
class ObservableCreator<T>(private val observableOnSubscribe: ObservableOnSubscribe<T>) : Observable<T>() {
    override fun subscribeActual(observer: Observer<T>?) {
        val observableEmitter = ObservableEmitter(observer)
        observer!!.onSubscribe(observableEmitter)
        // 最关键的调用
        observableOnSubscribe.subscribe(observableEmitter)
    }

    class ObservableEmitter<T>(private val observer: Observer<T>?) : AtomicReference<Disposable?>(), Emitter<T>, Disposable {
        override fun isDisposed(): Boolean {
            return DisposableHelper.isDisposed(get()!!)
        }

        override fun dispose() {
            DisposableHelper.dispose(this)
        }

        override fun onNext(t: T) {
            if (!isDisposed()) {
                observer!!.onNext(t)
            }
        }

        override fun onCompleted() {
            if (!isDisposed()) {
                observer!!.onCompleted()
            }
        }

        override fun onError(throwable: Throwable?) {
            if (!isDisposed()) {
                observer!!.onError(throwable)
            }
        }
    }
}