package com.mono.annotaton;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
    interface  IHello{
        void hello();
    }

    class Hello implements IHello{

        @Override
        public void hello() {
            System.out.println("hello");
        }
    }

    static class MyInvocationHanlder implements InvocationHandler {
        public <T> T create(Class<T>target){
            return (T) Proxy.newProxyInstance(target.getClassLoader(),
                    new Class[]{target},
                    this);
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 在原方法前插入动态代理
//            Object invoke = method.invoke(proxy, args);
            // 在原方法后边动态插入代码
            System.out.println( "method = " +method);
            return null;
        }
    }


    public static void main(String[] args) {
        MyInvocationHanlder m = new MyInvocationHanlder();
        IHello iHello = m.create(IHello.class);
        iHello.hello();
    }
}
