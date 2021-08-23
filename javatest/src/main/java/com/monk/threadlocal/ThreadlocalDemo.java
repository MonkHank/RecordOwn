package com.monk.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadlocalDemo {
    /**
     * threadLocalOne -> ThreadLocal是强引用
     * gc: 回收弱引用对象，是先回收弱引用的对象，弱引用链自然断开；而不是先断开引用链，再回收对象
     *
     * threadLocal对象是没法回收的！！！
     * @param args
     */
   /* public static void main(String[] args) {
        ThreadLocal<String> threadLocalOne = new ThreadLocal<>();

        new Thread(() -> {
            threadLocalOne.set("线程一的数据 --- threadLocal one");
            System.gc();
            System.out.println(threadLocalOne.get());
        }).start();
    }*/


    static ThreadLocal<String> threadLocalOne = new ThreadLocal<>();

    /**
     * 演示下threadLocalOne对ThreadLocal的引用链断开，Entry里面key引用被gc回收的情况
     * @param args
     */
/*    public static void main(String[] args) {
        new Thread(() -> {
            threadLocalOne.set("线程一的数据 --- threadLocalOne");
            try {
                threadLocalOne = null;
                System.gc();

                //下面代码来自：https://blog.csdn.net/thewindkee/article/details/103726942
                Thread t = Thread.currentThread();
                Class<? extends Thread> clz = t.getClass();
                Field field = clz.getDeclaredField("threadLocals");
                field.setAccessible(true);
                Object threadLocalMap = field.get(t);
                Class<?> tlmClass = threadLocalMap.getClass();
                Field tableField = tlmClass.getDeclaredField("table");
                tableField.setAccessible(true);
                Object[] arr = (Object[]) tableField.get(threadLocalMap);
                for (Object o : arr) {
                    if (o == null) continue;
                    Class<?> entryClass = o.getClass();
                    Field valueField = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
                    valueField.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.println(String.format("弱引用key:%s    值:%s", referenceField.get(o), valueField.get(o)));
                }
            } catch (Exception e) { }
        }).start();
    }*/
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();

        System.out.println(atomicInteger.getAndAdd(1));  //0
        System.out.println(atomicInteger.getAndAdd(1));  //1
        System.out.println(atomicInteger.getAndAdd(1));  //2

    }


}
