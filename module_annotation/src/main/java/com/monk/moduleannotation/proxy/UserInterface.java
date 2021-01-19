package com.monk.moduleannotation.proxy;

public interface UserInterface {

    default void sayHello(){
        System.out.println("-----UserInterface-----");
    }

    void eat();

    String go();
}
