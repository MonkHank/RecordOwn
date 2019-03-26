package com.monk.aidldemo;

/**
 * @author monk
 * @date 2019-03-22
 */
public class Student extends Person{
    public Student(String name, int age) {
        super(name, age);
    }

    @Override
    public void testParentReturn(int index) {
        super.testParentReturn(index);

        System.out.println("i am student");
    }
}
