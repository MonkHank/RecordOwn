package com.monk.aidldemo;

/**
 * @author monk
 * @date 2019-03-22
 */
public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void testParentReturn(int index) {
        if (index == 0) {
            return ;
        }

        System.out.println("i am parent");
    }
}
