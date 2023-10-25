package com.monk.designmode.stragety;

/**
 * @author monk
 * @date 2019-01-11
 */
public class Subway implements Calculator {

    @Override
    public float calculate(float km) {
        if (km < 2) {
            return (float) 1.6;
        }
        if (km < 10) {
            return (float) (1.8*(km-2)+1.6);
        }
        return (float) (2.0*(km-10)+1.6);
    }


}
