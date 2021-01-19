package com.monk.moduleannotation.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserHandler implements InvocationHandler {

    private final User user;

    public UserHandler(User user) {
        this.user = user;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println("proxy = "+proxy + "\tmethod = "+method+"\targs = "+args);
        System.out.println( "UserHandler - 1. method = "+method);
        System.out.println( "UserHandler - 2. args = "+args);
        System.out.println("-------UserHandler---------");
        method.invoke(user);


        return user;
    }
}
