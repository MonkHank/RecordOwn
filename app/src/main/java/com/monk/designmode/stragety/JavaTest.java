package com.monk.designmode.stragety;

/**
 * @author monk
 * @date 2019-01-11
 */
public class JavaTest {

    public static void main(String[] args) {
        Stragety stragety=new Stragety();

        stragety.setCalculator(new Bus());
        System.out.println( stragety.calculate(10));

        stragety.setCalculator(new Subway());
        System.out.println(stragety.calculate(10));
    }
}
