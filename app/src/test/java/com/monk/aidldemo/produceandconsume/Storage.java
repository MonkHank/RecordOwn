package com.monk.aidldemo.produceandconsume;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author monk
 * @date 2019/7/3
 */
public class Storage {
    private final int MAX_SIZE = 100;
    private List list = new LinkedList();

    public void produce(int num) {
        synchronized (list) {
            while (list.size() + num > MAX_SIZE) {
                System.out.println("暂时不能生产任务");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < num; i++) {
                list.add(new Object());
            }
            System.out.println("已生产" + num + "，仓库量" + list.size());
            list.notifyAll();
        }
    }

    public void consume(int num) {//消费num个产品
        synchronized (list) {
            while (list.size() < num) {
                System.out.println("暂时不能执行消费任务");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //满足消费条件开始消费
            for (int i = 0; i < num; i++) {
                list.remove(i);
            }
            System.out.println("已消费产品数" + num + " 仓库容量" + list.size());
            list.notifyAll();
        }
    }

    //生产者
    class Producer extends Thread {
        private int num;//生产的数量
        public Storage storage;//仓库

        public Producer(Storage storage) {
            this.storage = storage;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void run() {
            storage.produce(num);
        }
    }

    //消费者
    class Consumer extends Thread {
        private int num;//消费的数量
        public Storage storage;//仓库

        public Consumer(Storage storage) {
            this.storage = storage;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void run() {
            storage.consume(num);
        }
    }

    @Test
    public void testProduceAndConsume() {
        Storage storage = new Storage();
        Producer p1 = new Producer(storage);
        Producer p2 = new Producer(storage);
        Producer p3 = new Producer(storage);
        Producer p4 = new Producer(storage);
        Producer p5 = new Producer(storage);

        Consumer c1 = new Consumer(storage);
        Consumer c2 = new Consumer(storage);
        Consumer c3 = new Consumer(storage);
        p1.setNum(10);
        p2.setNum(20);
        p3.setNum(10);
        p4.setNum(80);
        p5.setNum(10);
        c1.setNum(50);
        c2.setNum(20);
        c3.setNum(20);
        c1.start();
        c2.start();
        c3.start();
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
    }
}
