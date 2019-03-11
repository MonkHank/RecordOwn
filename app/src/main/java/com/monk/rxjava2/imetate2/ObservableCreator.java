package com.monk.rxjava2.imetate2;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author monk
 * @date 2019-03-01
 */
public class ObservableCreator<T> extends Observable<T> {
    private ObservableOnSubscribe<T> observableOnSubscribe;

    public ObservableCreator(ObservableOnSubscribe<T> observableOnSubscribe) {
        this.observableOnSubscribe = observableOnSubscribe;
    }

    @Override
    public void subscribeActual(Observer<T> observer) {
        ObservableEmitter<T> observableEmitter = new ObservableEmitter<>(observer);
        observer.onSubscribe(observableEmitter);
        // 最关键的调用
        observableOnSubscribe.subscribe(observableEmitter);
    }

    public static final class ObservableEmitter<T> extends AtomicReference<Disposable> implements Emitter<T>, Disposable {
        private Observer<T> observer;

        public ObservableEmitter(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public void onNext(T t) {
            if (!isDisposed()) {
                observer.onNext(t);
            }
        }

        @Override
        public void onCompleted() {
            if (!isDisposed()) {
                observer.onCompleted();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!isDisposed()) {
                observer.onError(throwable);
            }
        }
    }
}
