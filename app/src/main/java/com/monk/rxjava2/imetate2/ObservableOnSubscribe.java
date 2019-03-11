package com.monk.rxjava2.imetate2;



/**
 * @author monk
 * @date 2019-03-01
 */
public interface ObservableOnSubscribe<T> {

    void subscribe(ObservableCreator.ObservableEmitter<T> emitter);
}
