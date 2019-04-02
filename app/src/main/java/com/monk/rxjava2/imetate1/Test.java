package com.monk.rxjava2.imetate1;

/**
 * @author monk
 * @date 2019-02-26
 */
public class Test {

    public static void main(String[] args) {
        Observable.create(new Observable.OnSubscriber<String>() {
            @Override
            public void call(Subscriber<String> stringSubscriber) {
                stringSubscriber.onNext("test");
                stringSubscriber.onComplete();
            }
            // 这个 subscriber 就是上面这个 subscriber，
            // 响应式编程，通过 subscriber 调用其自身 onNext、onComplete方法，然后执行该些方法
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String o) {
                System.out.println(o);
            }
        });
    }
}
