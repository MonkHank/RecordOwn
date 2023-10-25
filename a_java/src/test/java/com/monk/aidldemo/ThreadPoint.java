package com.monk.aidldemo;

import org.junit.runner.RunWith;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.xml.transform.Source;

public class ThreadPoint {
    private Thread threadA;

    // 1. start()
    // 2. run()
    // 3. join()
    // 4. currentThread()
    // 5. yield()
    // 6. sleep(ms)

    Thread testJoin() {
        System.out.println("--------------testJoin---------------");
        threadA = new Thread(() -> {
            // 对Thread来讲，run()函数start()之后由JVM调用的，

            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"：" + i+" - A线程状态："+threadA.getState());
            }
        },"ThreadA");
        threadA.start();

       return threadA;
    }

    public ThreadPoint() throws ExecutionException, InterruptedException {
        FutureTask<Person>ft =new FutureTask<>(()-> {
            Person jack = new Person("----ThreadPoint--------", 12);
            System.out.println(jack.toString());
            return jack;
        });
        Thread t1 = new Thread(ft);
        t1.start();


    }


    public static int count =0;

    static void testCAS() {
        System.out.println("--------------testCAS---------------");
        new Thread(()->{
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 10; i++) {
                count++;
            }
            System.out.println(Thread.currentThread()+"："+count);
        }).start();
    }

    static void testWaitAndSleep() throws InterruptedException {
        System.out.println("--------------testWaitAndSleep---------------");
        new Thread(new Runnable1(),"A").start();
        Thread.sleep(5000);
        new Thread(new Runnable2(),"B").start();
    }

    static class Runnable1 implements Runnable{
        @Override
        public void run() {
            synchronized (ThreadPoint.class) {

                System.out.println(Thread.currentThread()+"enter runnable1...");
                System.out.println(Thread.currentThread()+"runnable1 is waiting...");

                try {
                    ThreadPoint.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread()+"runnable1 is going on...");
                System.out.println(Thread.currentThread()+"thread1 is over!!!");
            }
        }
    }

    static class Runnable2 implements Runnable{
        @Override
        public void run() {
            synchronized (ThreadPoint.class) {

                System.out.println(Thread.currentThread()+"enter runnable2...");
                System.out.println(Thread.currentThread()+"runnable2 is sleeping...");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread()+"runnable2 is going on...");
                System.out.println(Thread.currentThread()+"thread2 is over!!!");

                ThreadPoint.class.notify();
            }
        }
    }



}
