package com.monk.modulefragment.rxjava2.imetate2

import java.util.concurrent.atomic.AtomicReference

/**
 * @author monk
 * @date 2019-03-01
 */
enum class DisposableHelper : Disposable {
    /***  */
    DISPOSABLE;

    override fun isDisposed(): Boolean {
        return false
    }

    override fun dispose() {}

    companion object {
        fun isDisposed(disposable: Disposable): Boolean {
            return disposable === DISPOSABLE
        }

        fun dispose(disposableAtomicReference: AtomicReference<Disposable?>): Boolean {
            var current = disposableAtomicReference.get()
            val d: Disposable = DISPOSABLE
            if (current !== d) {
                current = disposableAtomicReference.getAndSet(d)
                if (current !== d) {
                    current?.dispose()
                    return true
                }
            }
            return false
        }
    }
}