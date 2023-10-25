package com.monk.designmode.stragety;

/**
 * @author monk
 * @date 2019-01-11
 */
public class Bus implements Calculator {

    @Override
    public float calculate(float km) {
        return (float) (km * 1.6);
    }
}
