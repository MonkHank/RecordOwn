package com.monk.moduleannotation.proxy;

public class User implements UserInterface{
    @Override
    public void sayHello() {
        System.out.println("I am user");
    }

    @Override
    public void eat() {

    }

    @Override
    public String go() {
        return null;
    }
}
