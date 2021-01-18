package com.monk.moduleannotation.proxy;

import java.lang.reflect.InvocationHandler;

public class UserProxy implements UserInterface{

    private InvocationHandler handler;

    public UserProxy(InvocationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void sayHello() {
        System.out.println("--------UserProxy---------");
        try {
            System.out.println("UserProxy - 1. this = "+this);
            handler.invoke(this, UserInterface.class.getMethod("sayHello"), null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void eat() {

    }

    @Override
    public String go() {
        System.out.println("--------UserProxy---------");
        try {
            handler.invoke(this, UserInterface.class.getMethod("go"), null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
