package com.monk.moduleannotation.reflect;


import org.greenrobot.eventbus.Subscribe;

import retrofit2.http.Query;

/**
 * @author monk
 * @date 2019-04-02
 */
public class ReflectClass<T> implements IInterface{
    String name;
    private int age;

    /**
     * private 通过newInstance() 获取会失败
     */
    public ReflectClass() {
    }

    public ReflectClass(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public ReflectClass<T> setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int sex, int age) {
        this.age = age;
    }

    private void privateMethod() {
    }


    static void staticMethod(){}

    @Subscribe
    void testSub() {

    }


    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    class EventBusBean{}

    @Override
    public void postLogin(@Query("api/test") String path) {

    }
}
