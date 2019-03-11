package com.monk.rxjava2.imetate2;

/**
 * 发射数据
 * @author monk
 * @date 2019-03-01
 */
public interface Emitter<T> {
    void onNext(T t);

    void onCompleted();

    void onError(Throwable throwable);
}
