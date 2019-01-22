package com.monk.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author monk
 * @date 2019-01-22
 */
public class UseMyAnnotation {

    @MyAnnotation(name="aha",value = 10)
    public void show(String name) {
        System.out.println(name);
    }

    public static void main(String[] args) {
        Class<UseMyAnnotation> clazz = UseMyAnnotation.class;
        try {
            Method show = clazz.getMethod("show", String.class);
            MyAnnotation annotation = show.getAnnotation(MyAnnotation.class);
            System.out.println(annotation.name()+"\t"+annotation.value());
            show.invoke(clazz.newInstance(), "hh");

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
