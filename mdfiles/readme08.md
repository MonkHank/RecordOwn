# 生产者消费者，面向过程设计模式
>缓冲队列，第三者充当解耦

java.lang.IllegalMonitorStateException；synchronized 方法的 object's monitor 的 object 是 this；<br>

生产者是一堆线程，消费者是另一堆线程，内存缓冲区可以使用List数组队列，数据类型只需要定义一个简单的类就好。关键是如何处理多线程之间的协作。这其实也是多线程通信的一个范例。

在这个模型中，**最关键**就是内存缓冲区为空的时候消费者必须等待，而内存缓冲区满的时候，生产者必须等待。

[博文](https://www.cnblogs.com/chentingk/p/6497107.html)