package com.monk.moduleannotation.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author monk
 * @date 2019-01-22
 */
public class UseMyAnnotation {

    @MyAnnotation(name="aha",value = 10)
    public void show(String name) {
        System.out.println("函数调用成功："+name);
    }

    public static void main(String[] args) {
        Class<UseMyAnnotation> clazz = UseMyAnnotation.class;
        try {

            // 根据 方法名，参数类型 返回方法
            Method show = clazz.getMethod("show", String.class);

            MyAnnotation annotation = show.getAnnotation(MyAnnotation.class);
            System.out.println("注解名和值："+annotation.name()+"\t"+annotation.value());

            // 方法调用，传入对象、方法实参，返回方法返回值
            Object hh = show.invoke(clazz.newInstance(), "hh");
            System.out.println(hh);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
