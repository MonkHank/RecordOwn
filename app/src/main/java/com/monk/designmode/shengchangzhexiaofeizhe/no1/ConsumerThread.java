package com.monk.designmode.shengchangzhexiaofeizhe.no1;

/**
 * @author monk
 * @date 2019-04-16
 */
public class ConsumerThread implements  Runnable {

    Consumer c;
    public ConsumerThread(Consumer c) {
        this.c=c;
    }

    @Override
    public void run() {
        c.cosume();
    }
}
