package com.monk.designmode;


/**
 * @author monk
 * @date 2019-01-09
 */
public class Test {
    public static void main(String[] args) {

        testLocalVariableFinal();
        twiceCreate();

    }

    public static void testLocalVariableFinal() {
        final Apple apple = new Apple();
        System.out.println(apple.hashCode());
    }

    public static void twiceCreate() {
        testLocalVariableFinal();
    }
}

class Apple{

    String red;

    String shape;

    @Override
    public String toString() {
        return "Apple{" +
                "red='" + red + '\'' +
                ", shape='" + shape + '\'' +
                '}';
    }

}
