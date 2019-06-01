package com.monk.rxjava2.imetate1;

/**
 * @author monk
 * @date 2019-02-26
 */
public abstract class Subscriber<T> implements Observer<T>, Subsription {
    private volatile boolean isSubscribe;

    @Override
    public void unSubsribe() {
        isSubscribe = true;
    }

    @Override
    public boolean isUnSubscribe() {
        return isSubscribe;
    }
}
