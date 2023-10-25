package com.monk.designmode.builder;

/**
 * @author monk
 * @date 2019-01-09
 */
public class JavaTest {
    public static void main(String[] args) {
        Builder builder = new MacbookBuilder();
        Director director = new Director();

        director.direct(builder);
        System.out.println(director.toString());
    }
}
