package com.monk.shengchangzhexiaofeizhe;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class ProductorConsumerDemo2 {

    public static void main(String[] args) {
        PublicQueue queue = new PublicQueue();
        ExecutorService service = Executors.newFixedThreadPool(15);
        for (int i=0;i<5;i++){
            service.submit(new Producer(queue));
        }
        for (int i=0;i<10;i++){
            service.submit(new Consmer(queue));
        }
    }

    static class Producer implements Runnable{
        private PublicQueue queue;

        public Producer(PublicQueue queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            while (true){
                try {
                    Random random = new Random();
                    int i = random.nextInt();
                    queue.add(i);
                }catch (Exception e){}
            }
        }
    }

    static class Consmer implements Runnable{
        private PublicQueue queue;

        public Consmer(PublicQueue queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            while (true){
                try {
                    queue.get();
                }catch (Exception e){}
            }
        }
    }

    static class PublicQueue{
        private BlockingDeque<Integer>blockingDeque= new LinkedBlockingDeque<>(50);

        public void add(Integer i){
            try {
                blockingDeque.put(i);
                System.out.println("生产者"+Thread.currentThread().getName()+"生产"+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public Integer get(){
            Integer i =null;
            try {
                i = blockingDeque.take();
                System.out.println("消费者"+Thread.currentThread().getName()+"消费"+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        }
    }
}
