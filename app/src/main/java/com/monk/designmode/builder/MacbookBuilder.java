package com.monk.designmode.builder;

/**
 * @author monk
 * @date 2019-01-09
 */
public class MacbookBuilder extends Builder {
    @Override
    public Product create() {
        Product product = new Macbook();
        product.setBrand(null);
        product.setModel(null);
        return  product;
    }
}
