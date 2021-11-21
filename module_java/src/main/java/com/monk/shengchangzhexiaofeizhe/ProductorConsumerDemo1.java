package com.monk.shengchangzhexiaofeizhe;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductorConsumerDemo1 {


    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        ExecutorService service = Executors.newFixedThreadPool(15);
        for(int i=0;i<5;i++){
            service.submit(new Productor(linkedList, 8));
        }

        for(int i=0;i<10;i++){
            service.submit(new Consumer(linkedList));
        }

    }

    static class Productor implements Runnable{
        private List<Integer>list;
        private int maxLenghth;
        public Productor(List<Integer>list,int maxLength){
            this.list=list;
            this.maxLenghth=maxLength;
        }

        @Override
        public void run() {
            while(true){
                synchronized (list){
                    try{
                        if (list.size()==maxLenghth){
                            list.wait();
                        }
                        Random random = new Random();
                        int i=random.nextInt();
                        System.out.println("生产者"+Thread.currentThread().getName()+"-"+"生产数据"+i);
                        list.add(i);
                        list.notifyAll();
//                        Thread.sleep(500);
                    }catch (Exception e){

                    }
                }
            }
        }
    }

    static class Consumer implements Runnable{
        private List<Integer>list;
        public Consumer(List<Integer>list){
            this.list=list;
        }

        @Override
        public void run() {
            while(true){
                try {

                    synchronized (list){
                        if (list.isEmpty()){
                            list.wait();
                        }
                        Integer e = list.remove(0);
                        System.out.println("消费者"+Thread.currentThread().getName()+"消费数据"+e);
                        list.notifyAll();
//                        Thread.sleep(500);
                    }
                }catch (Exception e){}
            }
        }
    }
}
