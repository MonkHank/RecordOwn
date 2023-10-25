package com.monk.designmode.builder;

/**
 * @author monk
 * @date 2019-01-09
 */
public class Director {

    private Product product;

    public Product direct(Builder builder) {
        product = builder.create();
        return product;
    }

    @Override
    public String toString() {
        return product.toString();
    }
}
