package com.monk.rxjava2.imetate1;

/**
 * @author monk
 * @date 2019-02-26
 */
public class Observable<T>{
    interface OnSubscriber<T> extends Action1<Subscriber<T>>{
    }

    private OnSubscriber<T> onSubscriber;

    private Observable(OnSubscriber<T> onSubscriber) {
        this.onSubscriber=onSubscriber;
    }

    public static <T> Observable<T> create(OnSubscriber<T> onSubscriber) {
        return new Observable<>(onSubscriber);
    }

    public  Observer<T> subscribe(Subscriber<T> subscriber) {
        this.onSubscriber.call(subscriber);
        return subscriber;
    }
}
