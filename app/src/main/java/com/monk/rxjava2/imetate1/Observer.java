package com.monk.rxjava2.imetate1;

/**
 * @author monk
 * @date 2019-02-26
 */
public interface Observer<T> {
    void onComplete();

    void onError(Throwable e);

    void onNext(T t);
}
