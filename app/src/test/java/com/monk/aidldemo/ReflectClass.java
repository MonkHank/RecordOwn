package com.monk.aidldemo;

/**
 * @author monk
 * @date 2019-04-02
 */
public class ReflectClass {
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

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    private void privateMethod() {

    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
