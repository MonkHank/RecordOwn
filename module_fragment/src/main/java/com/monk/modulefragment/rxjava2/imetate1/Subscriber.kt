package com.monk.modulefragment.rxjava2.imetate1

/**
 * @author monk
 * @date 2019-02-26
 */
abstract class Subscriber<T> : Observer<T>, Subsription {
    @Volatile
    private var isSubscribe = false
    override fun unSubsribe() {
        isSubscribe = true
    }

    override fun isUnSubscribe(): Boolean {
        return isSubscribe
    }
}