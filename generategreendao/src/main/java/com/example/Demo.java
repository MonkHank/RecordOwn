package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * @author monk
 * @date 2018-08-30.
 */

public class Demo {


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException, IOException {

        Class<?> personClass = Class.forName("com.example.Person");

//        Method init = personClass.getMethod("init");
//        init.invoke(personClass.newInstance());
//        Method getResult = personClass.getMethod("getResult", int.class, int.class);
//        Object invoke = getResult.invoke(personClass.newInstance(), 10, 20);
//        System.out.println(invoke);

        Object o = personClass.newInstance();
        Field name = personClass.getDeclaredField("name");
        name.setAccessible(true);
        name.set(o, "nancy");
        System.out.println(o);

        Properties pro = new Properties();
        File f = new File("fruit.properties");
        if (f.exists()) {
            pro.load(new FileInputStream(f));
            System.out.println(pro.getProperty("apple"));
        }else {
            pro.setProperty("apple", "com.example.Person");
            pro.store(new FileOutputStream(f), "FRUIT CLASS");
        }


    }
}

class Person implements IPresnet {
    private String name = "andy";
    int age = 10;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "name" + name + "age" + age;
    }

    @Override
    public void init() {
        System.out.println("init");
    }

    @Override
    public int getResult(int a, int b) {
        System.out.println(a + b);
        return a + b;
    }
}

interface IPresnet {
    int PARENT = 0;

    void init();

    int getResult(int a, int b);
}


