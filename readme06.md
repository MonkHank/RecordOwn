# 事件分发机制
- [图解android事件分发机制](https://www.jianshu.com/p/e99b5e8bd67b)
- [通俗理解Android事件分发与消费机制](https://www.cnblogs.com/wytiger/p/5235393.html)

![](/picture/事件分发机制.jpg)

>一点分析

## Android事件分发机制（上）
首先你需要知道一点，只要触摸到任何一个控件，就一定会调用该控件的dispatchOnTouchEvent方法。
dispatchTouchEvent(MotionEvent)源码
![](/picture/dispatchTouchEvent.webp)

上述是很早之前版本源码，现在多了些判断，本质还是一样的，首先控件注册了touch事件，控件必须是enabled，控件注册的touch事件的ouTouch回调函数返回true，以上满足后才会去执行onTouchEvent方法，dispatchTouchEvent方法最先执行的是onTouch方法。

根据以上源码分析，还透露一个很重要的信息，那就是onClick调用肯定是在onTouchEvent方法中调用。

具体调用看onTouchEvent方法源码会发现，在ACTION_UP中会经过一些列判断最终执行performClick方法，而performClick方法会判断当前mOnClickListener是否为null，从而执行它里面的onClick方法。
![](/picture/onTouchEvent1.webp)
![](/picture/onTouchEvent2.webp)
![](/picture/onTouchEvent3.webp)

如果将button换成imageview的话，执行onTouch方法，返回false，进入onTouchEvent里面，

在ACTION_DOWN执行完后，后面的一系列action都不会得到执行了。这又是为什么呢？因为ImageView和按钮不同，它是默认不可点击的，因此在onTouchEvent的第14行判断时无法进入到if的内部，直接跳到第91行返回了false，也就导致后面其它的action都无法执行了。

根据以上分析可知：
<font color="de87">**如果一个控件是可点击的，那么点击该控件时，dispatchTouchEvent的返回值必定是true（super.OnTouchEvent(ev)结果为true，89行）**</font>

### onTouch和onTouchEvent有什么区别，又该如何使用？
从源码中可以看出，这两个方法都是在View的dispatchTouchEvent中调用的，onTouch优先于onTouchEvent执行。如果在onTouch方法中通过返回true将事件消费掉，onTouchEvent将不会再执行。

另外需要注意的是，onTouch能够得到执行需要两个前提条件，第一mOnTouchListener的值不能为空，第二当前点击的控件必须是enable的。因此如果你有一个控件是非enable的，那么给它注册onTouch事件将永远得不到执行。对于这一类控件，如果我们想要监听它的touch事件，就必须通过在该控件中重写onTouchEvent方法来实现。

## Android事件分发机制（下）
你会发现，不管你点击哪里，永远都只会触发MyLayout的touch事件了，按钮的点击事件完全被屏蔽掉了！这是为什么呢？如果Android中的touch事件是先传递到View，再传递到ViewGroup的，那么MyLayout又怎么可能屏蔽掉Button的点击事件呢？

搞清楚Android中ViewGroup的事件分发机制，才能解决我们心中的疑惑了，不过这里可以透露一句，<font color="00574B">**Android中touch事件的传递，绝对是先传递到ViewGroup，再传递到View的，**</font>记得在（上）中说过这么一句，**只要你触摸了任何控件，就一定会调用该控件的dispatchTouchEvent方法。这个说法没错，只不过还不完整而已。** 实际情况是，当你点击了某个控件，首先会去调用该控件所在布局的dispatchTouchEvent方法，然后在布局的dispatchTouchEvent方法中找到被点击的相应控件，再去调用该控件的dispatchTouchEvent方法。
![](/picture/dipatchTouchEvent2.webp)
![](/picture/dipatchTouchEvent3.webp)

这个方法代码比较长，我们只挑重点看。首先在第13行可以看到一个条件判断，如果disallowIntercept和!onInterceptTouchEvent(ev)两者有一个为true，就会进入到这个条件判断中。disallowIntercept是指是否禁用掉事件拦截的功能，默认是false，也可以通过调用requestDisallowInterceptTouchEvent方法对这个值进行修改。<br>

那么当第一个值为false的时候就会完全依赖第二个值来决定是否可以进入到条件判断的内部，第二个值是什么呢？竟然就是对onInterceptTouchEvent方法的返回值取反！也就是说如果我们在onInterceptTouchEvent方法中返回false，就会让第二个值为true，从而进入到条件判断的内部，如果我们在onInterceptTouchEvent方法中返回true，就会让第二个值为false，从而跳出了这个条件判断。<br>

这个时候你就可以思考一下了，由于我们刚刚在MyLayout中重写了onInterceptTouchEvent方法，让这个方法返回true，导致所有按钮的点击事件都被屏蔽了，那我们就完全有理由相信，按钮点击事件的处理就是在第13行条件判断的内部进行的！<br>

那我们重点来看下条件判断的内部是怎么实现的。在第19行通过一个for循环，遍历了当前ViewGroup下的所有子View，然后在第24行判断当前遍历的View是不是正在点击的View，如果是的话就会进入到该条件判断的内部，然后在第29行调用了该View的dispatchTouchEvent，之后的流程就和 Android事件分发机制完全解析，带你从源码的角度彻底理解(上) 中讲解的是一样的了。我们也因此证实了，按钮点击事件的处理确实就是在这里进行的。<br>

然后需要注意一下，调用子View的dispatchTouchEvent后是有返回值的。我们已经知道，<font color="de87">**如果一个控件是可点击的，那么点击该控件时，dispatchTouchEvent 的返回值必定是true。**</font>因此会导致第29行的条件判断成立，于是在第31行给ViewGroup的dispatchTouchEvent方法直接返回了true。这样就导致后面的代码无法执行到了，也是印证了我们前面的Demo打印的结果，如果按钮的点击事件得到执行，就会把MyLayout的touch事件拦截掉。

那如果我们点击的不是按钮，而是空白区域呢？这种情况就一定不会在第31行返回true了，而是会继续执行后面的代码。那我们继续往后看，在第44行，如果target等于null，就会进入到该条件判断内部，这里一般情况下target都会是null，因此会在第50行调用super.dispatchTouchEvent(ev)。这句代码会调用到哪里呢？当然是View中的dispatchTouchEvent方法了，因为ViewGroup的父类就是View。之后的处理逻辑又和前面所说的是一样的了，也因此MyLayout中注册的onTouch方法会得到执行。之后的代码在一般情况下是走不到的了，我们也就不再继续往下分析。

再看一下整个ViewGroup事件分发过程的流程图吧，相信可以帮助大家更好地去理解：
![](/picture/event流程图.webp)
现在整个ViewGroup的事件分发流程的分析也就到此结束了，我们最后再来简单梳理一下吧。

1. Android事件分发是先传递到ViewGroup，再由ViewGroup传递到View的。

2. 在ViewGroup中可以通过onInterceptTouchEvent方法对事件传递进行拦截，onInterceptTouchEvent方法返回true代表不允许事件继续向子View传递，返回false代表不对事件进行拦截，默认返回false。

3. 子View中如果将传递的事件消费掉，ViewGroup中将无法接收到任何事件。

好了，Android事件分发机制完全解析到此全部结束，结合上下两篇，相信大家对事件分发的理解已经非常深刻了。
![](/picture/几个事件分发回调函数.webp)

### ACTION_MOVE 和 ACTION_UP 事件的分发和处理规则
所以我们就基本可以得出结论如果在某个控件的dispatchTouchEvent 返回true消费终结事件，那么收到ACTION_DOWN 的函数（dispatch和 onTouchEvent）也能收到 ACTION_MOVE和ACTION_UP。

在哪个View的onTouchEvent 返回true，那么ACTION_MOVE和ACTION_UP的事件从上往下传到这个View后就不再往下传递了，而直接传给自己的onTouchEvent 并结束本次事件传递过程。

对于ACTION_MOVE、ACTION_UP总结：ACTION_DOWN事件在哪个控件消费了（return true），  那么ACTION_MOVE和ACTION_UP就会从上往下（通过dispatchTouchEvent）做事件分发往下传，就只会传到这个控件，不会继续往下传，如果ACTION_DOWN事件是在dispatchTouchEvent消费，那么事件到此为止停止传递，如果ACTION_DOWN事件是在onTouchEvent消费的，那么会把ACTION_MOVE或ACTION_UP事件传给该控件的onTouchEvent处理并结束传递。

