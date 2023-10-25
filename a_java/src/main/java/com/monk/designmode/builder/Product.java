package com.monk.designmode.builder;

/**
 * @author monk
 * @date 2019-01-09
 */
public abstract class Product {
    String model = "init";
    String cpu = "init";
    String brand = "init";

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Product{" +
                "model='" + model + '\'' +
                ", cpu='" + cpu + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
