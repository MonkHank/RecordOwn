package com.monk.modulefragment.rxjava2.imetate1

/**
 * @author monk
 * @date 2019-02-26
 */
class Observable<T>
/**
 * ① Observable
 *
 * @param onSubscriber
 */ private constructor(private val onSubscriber: OnSubscriber<T>) {
    /**
     * ②OnSubscriber，发布。在其子类中调用 Subscriber 发布自身消息。
     *
     * @param <T>
    </T> */
    interface OnSubscriber<T> : Action1<Subscriber<T>?>

    /**
     * ③ Subscriber，订阅。订阅后才会接收发布的消息
     *
     * @param subscriber
     * @return
     */
    fun subscribe(subscriber: Subscriber<T>): Observer<T> {
        // 这个是最核心的调用，发布/订阅关系，发布与订阅之间的绑定；
        onSubscriber.call(subscriber)
        return subscriber
    }

    companion object {
        @JvmStatic
        fun <T> create(onSubscriber: OnSubscriber<T>): Observable<T> {
            return Observable(onSubscriber)
        }
    }
}