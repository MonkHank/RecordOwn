package com.monk.moduleannotation.reflect;

public class Person {
    /*** 测试反射 final 变量 */
    private final String name = "Bob";
    public final int age = 10;
    public final Student student = new Student();

    /*** 测试反射 static 变量*/
    private static final String sex = "女";

    public String getName() {
        return name;
    }
}
