package com.monk.rxjava2.imetate2;

/**
 * @author monk
 * @date 2019-03-01
 */
public class Test {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableCreator.ObservableEmitter<String> emitter) {
                emitter.onNext("test");
                emitter.onNext("tes1");
                emitter.onCompleted();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String str) {
                System.out.println(str);
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });


        ObservableOnSubscribe<String> observableOnSubscribe = new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableCreator.ObservableEmitter<String> emitter) {
                emitter.onNext("1");
                emitter.onNext("2");
            }
        };

        ObservableCreator<String> creator = new ObservableCreator<>(observableOnSubscribe);

        creator.subscribeActual(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(String o) {
                System.out.println(o);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }
}
