package com.monk.designmode.shengchangzhexiaofeizhe.no2;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author monk
 * @date 2019-04-16
 */
public class Consumer implements Runnable{
    private volatile boolean isRunning=true;
    private BlockingQueue<PcData>queue;
    private static AtomicInteger count=new AtomicInteger();
    private static final int SLEEP_TIME=1000;

    public Consumer(BlockingQueue<PcData>queue) {
        this.queue=queue;

    }


    @Override
    public void run() {
        System.out.println("消费者 -- "+Thread.currentThread().getId());
        Random r=new Random();
        try {
            while(true){
                PcData data = queue.take();
                if (data != null) {
                    int re=data.id*data.id;
                    System.out.println("consumer-"+Thread.currentThread().getId()
                            +":"+MessageFormat.format("{0}*{1}={2}",data.id,data.id,re)
                            +" - size()="+queue.size());
                    Thread.sleep(SLEEP_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
