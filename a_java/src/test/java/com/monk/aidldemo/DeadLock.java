package com.monk.aidldemo;

/**
 * @author monk
 * @date 2019-04-17
 */
public class DeadLock implements Runnable{

    public int flag = 1;


    //静态对象是类的所有对象共享的
    private static final Object o1 = new Object();
    private static final Object o2 = new Object();
    @Override
    public void run() {
        System.out.println("flag=" + flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("1");
                }
            }
        }
        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("0");
                }
            }
        }
    }
}

