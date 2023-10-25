package com.monk.designmode.shengchangzhexiaofeizhe.no1;

import java.util.ArrayList;

/**
 * @author monk
 * @date 2019-04-16
 */
public class Producer {

    ArrayList<Integer> list;
    Object obj;

    public Producer(Object obj, ArrayList<Integer> list) {
        this.obj=obj;
        this.list=list;
    }

    public  void produce(int num) {
        synchronized (obj) {
            while (!list.isEmpty()) {
                System.out.println("生产者："+Thread.currentThread().getName()+"...wait");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            list.add(num);
            System.out.println("生产者:"+Thread.currentThread().getName()+"...生产:"+num);
            obj.notify();

        }
    }


}
