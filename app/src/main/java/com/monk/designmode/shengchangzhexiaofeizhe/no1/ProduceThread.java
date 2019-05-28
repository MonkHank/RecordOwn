package com.monk.designmode.shengchangzhexiaofeizhe.no1;

/**
 * @author monk
 * @date 2019-04-16
 */
public class ProduceThread implements  Runnable {

    Producer producer;
    int num;
    public ProduceThread(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        producer.produce(num);
    }

    public void setNum(int num) {
        this.num=num;
    }
}
