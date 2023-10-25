package com.monk.designmode.shengchangzhexiaofeizhe.no1;

import java.util.ArrayList;

/**
 * @author monk
 * @date 2019-04-16
 */
public class Consumer {
    ArrayList<Integer> list;
    Object obj;

    public Consumer(Object obj, ArrayList<Integer> list) {
        this.obj=obj;
        this.list=list;
    }

    public  void cosume() {
        synchronized (obj) {
            while (list.isEmpty()) {
                System.out.println("消费者"+Thread.currentThread().getName()+"...wait");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Integer integer = list.get(0);
            list.clear();
            System.out.println("消费者:"+Thread.currentThread().getName()+"...消费："+integer);
            obj.notify();

        }
    }

}
