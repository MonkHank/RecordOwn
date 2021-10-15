# kotlin
1. 变量与常量
  - var 声明变量
  - val 声明常量
  - 不同的数据类型用不同的容器保存
  - kotlin会通过类型自动推断数据类型
  - 我们也可以显式的指定数据类型
- java 中常量有三种类型：静态常量、成员常量和局部常量。
    - [深入理解java虚拟机片段](https://www.cnblogs.com/syp172654682/p/8082625.html)
    - public static final double PI = 3.14;
    - final int y = 10;
    - 方法内部的final定义的常量
- [kotlin中的单例](https://www.jianshu.com/p/5797b3d0ebd0)
2. [kotlin扩展函数](https://blog.csdn.net/u013064109/article/details/79968846)
    - 接收者类型.方法名 = 接收者对象{...}
- [let、with、run、apply、also](https://blog.csdn.net/u013064109/article/details/78786646)
    - 扩展函数，接收高阶函数作为方法形参
    - let/also 都是扩展函数，前者返回用户定义的，后者返回接收者对象
    - with不是扩展函数，接收一个实例和扩展函数lambada表达式，返回lambada的返回
    - run是let和with的结合，返回的是用户自定义
    - apply和run唯一区别就是返回的是接收者对象本身
3.  [Kotlin (一) 复合符号( '?.' '?:' '!!' 'as?' '?' )](https://blog.csdn.net/lckj686/article/details/80448471)
4.  [kotlin 中得一些for语句](https://www.jianshu.com/p/27646c6561a7)
5.  [菜鸟教程，kotlin循环控制](https://www.runoob.com/kotlin/kotlin-loop-control.html)
6. by关键字
  - 代理（委托）模式

## 位运算符
- [kotlin位操作和位运算](https://blog.csdn.net/jdsjlzx/article/details/108606777)

## object
- [object关键字的使用场景](https://blog.csdn.net/xlh1191860939/article/details/79460601)
