package com.monk.aidldemo.produceandconsume;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author monk
 * @date 2019/7/4
 */
public class WaitNotify {

    final static Object lock=new Object();

    /**
     * 阻塞，不会执行全部代码
     */
    @Test
    public void testWaitNofity() {
        new Thread(() -> {
            System.out.println("线程A 等待，拿锁");
            synchronized (lock) {
                try {
                    System.out.println("线程 A 拿到锁了");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("线程 A 开始等待并放弃锁");
                    lock.wait();
                    System.out.println("被通知可以继续执行 则 继续运行至结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程A").start();

        new Thread(() -> {
            System.out.println("线程B 等待，拿锁");
            synchronized (lock) {
                try {
                    System.out.println("线程 B 拿到锁了");
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("线程 B 开始等待并放弃锁");
                    lock.notify();
                    System.out.println("线程 B 通知 lock 对象的某个线程");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程B").start();
    }

    /**
     * 可以执行完成
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("线程A 等待，拿锁");
            synchronized (lock) {
                try {
                    System.out.println("线程 A 拿到锁了");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("线程 A 开始等待并放弃锁 -- 1 s");
                    lock.wait();
                    System.out.println("线程 A 被通知可以继续执行 则 继续运行至结束 -- 5 s");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程A").start();

        new Thread(() -> {
            System.out.println("线程 B 等待，拿锁");
            synchronized (lock) {
                try {
                    System.out.println("线程 B 拿到锁了 -- 1 s");
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("线程 B 开始等待并放弃锁 -- 5 s");
                    lock.notify();
                    System.out.println("线程 B 通知 lock 对象的某个线程 -- 5 s");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程B").start();
    }
}
