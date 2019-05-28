# RxJava一些东西

## 响应式编程
**通过异步和数据流来构建事务关系的编程模型。**“事务的关系”是响应式编程的核心理念，“数据流”和“异步”是实现这个核心理念的关键。

1. 一种面向数据流和变化传播的编程范式;
2. 基于注册回调的方式运行;
3. 数据流，通过onNext()顺序发射数据;

- [重新理解响应式编程](https://www.jianshu.com/p/c95e29854cb1)


## RxJava1
> 五大元素
1. Observable、**OnSubscriber**、Observer、Subsription、Subscriber

## RxJava2
1. Observable、**ObservableOnSubscribe**、Observer、Emitter、Disposable
1. (无背压) Observable、Observer、Disposable、OnSubscribe、Emitter
2. (有背压) Flowable、Subscriber、Subscription、OnSubscribe、Emitter


- [Carson_Ho](https://www.jianshu.com/nb/14302692)
- [RxJava：这是一份全面 & 详细 的RxJava操作符 使用攻略](https://www.jianshu.com/p/cd984dd5aae8)
- [rxjava2的disposable](https://blog.csdn.net/c_j33/article/details/78774546)
- [Retrofit2.0](https://blog.csdn.net/carson_ho/article/details/73732076)

- [x] 一般用 **Retrofit** 需要结合 **Rxjava** 一起使用；听说很多公司现在重构代码用这种模式去替代OKHttp