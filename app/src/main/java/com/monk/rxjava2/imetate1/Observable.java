package com.monk.rxjava2.imetate1;

/**
 * @author monk
 * @date 2019-02-26
 */
public class Observable<T> {
    /**
     * ②OnSubscriber，发布。在其子类中调用 Subscriber 发布自身消息。
     *
     * @param <T>
     */
    interface OnSubscriber<T> extends Action1<Subscriber<T>> {
    }

    private OnSubscriber<T> onSubscriber;

    /**
     * ① Observable
     *
     * @param onSubscriber
     */
    private Observable(OnSubscriber<T> onSubscriber) {
        this.onSubscriber = onSubscriber;
    }

    public static <T> Observable<T> create(OnSubscriber<T> onSubscriber) {
        return new Observable<>(onSubscriber);
    }

    /**
     * ③ Subscriber，订阅。订阅后才会接收发布的消息
     *
     * @param subscriber
     * @return
     */
    public Observer<T> subscribe(Subscriber<T> subscriber) {
        // 这个是最核心的调用，发布/订阅关系，发布与订阅之间的绑定；
        this.onSubscriber.call(subscriber);
        return subscriber;
    }
}
