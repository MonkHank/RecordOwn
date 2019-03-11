package com.monk.rxjava2.imetate2;

/**
 * @author monk
 * @date 2019-03-01
 */
public interface Observer<T> {
    void onSubscribe(Disposable disposable);

    void onNext(T t);

    void onCompleted();

    void onError(Throwable throwable);
}
