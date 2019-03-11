package com.monk.rxjava2.imetate2;

/**
 * @author monk
 * @date 2019-03-01
 */
public abstract class Observable<T> {

    public static <T> Observable<T> create(ObservableOnSubscribe<T> observableOnSubscribe) {
        return new ObservableCreator<>(observableOnSubscribe);
    }

    public void subscribe(Observer<T> observer) {
        subscribeActual(observer);
    }

    public abstract void subscribeActual(Observer<T> observer);

}
