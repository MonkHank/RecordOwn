package com.monk.moduleannotation.proxy;

import java.lang.reflect.Proxy;

public class TestProxy {
    /**
     * 测试动态代理
     */
    public static void main(String[]arg) {
        // 1. 常规
        User user = new User();
        UserHandler userHandler = new UserHandler(user);
        Object o = Proxy.newProxyInstance(TestProxy.class.getClassLoader(), new Class[]{UserInterface.class}, userHandler);
        ((UserInterface)o).sayHello();

        // 2. 理解
        UserProxy userProxy = new UserProxy((proxy, method, args) -> {
            System.out.println("JavaTest - 1. method = "+method);
            System.out.println("JavaTest - 2. args = "+args);
            System.out.println("JavaTest - 3. getDeclaringClass = "+method.getDeclaringClass());
            System.out.println("JavaTest - 4. isDefault = "+method.isDefault());
            System.out.println("JavaTest - 5. getGenericReturnType = "+method.getGenericReturnType()+"  "+(method.getGenericReturnType() instanceof Class<?>));


            User user1 = new User();
            method.invoke(user1);
            return user1;
        });
        userProxy.sayHello();
        userProxy.go();
    }

}
