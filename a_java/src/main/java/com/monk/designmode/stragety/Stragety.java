package com.monk.designmode.stragety;

/**
 * @author monk
 * @date 2019-01-11
 */
public class Stragety {

    private Calculator calculator;

    public Calculator getCalculator() {
        return calculator;
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    public float calculate(float km) {
        return calculator.calculate(km);
    }
}
