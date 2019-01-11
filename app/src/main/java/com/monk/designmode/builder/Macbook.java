package com.monk.designmode.builder;

/**
 * @author monk
 * @date 2019-01-09
 */
public class Macbook extends Product {
    @Override
    public void setBrand(String brand) {
        brand = "Mac";
        super.setBrand(brand);
    }

    @Override
    public void setModel(String model) {
        model = "ASUS";
        super.setModel(model);
    }
}
