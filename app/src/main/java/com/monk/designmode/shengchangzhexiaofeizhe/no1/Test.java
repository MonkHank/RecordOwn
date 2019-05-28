package com.monk.designmode.shengchangzhexiaofeizhe.no1;

import java.util.ArrayList;

/**
 * @author monk
 * @date 2019-04-16
 */
public class Test {

    public static void main(String[] args) {
        Object obj = new Object();
        ArrayList<Integer> list = new ArrayList<>();

        Consumer c=new Consumer(obj,list);
        Producer p=new Producer(obj,list);

        ConsumerThread ct=new ConsumerThread(c);
        ProduceThread pt=new ProduceThread(p);

        for (int i=0;i<5;i++){
            pt.setNum(i);
            Thread t1 = new Thread(ct);
            Thread t2 = new Thread(pt);

            t2.start();
            t1.start();
        }



    }


}
