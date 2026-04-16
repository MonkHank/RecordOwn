package com.monk.moduleannotation.reflect;

public class Person {
    /*** 测试反射 final 变量 */
    private final String finalName = "Bob";
    private final int finalAge = 10;
    public final Student student = new Student();

    /*** 测试反射 static 变量*/
    private static  String staticGender = "女";
    // private static final String staticField = "女"; // 如果final 修饰 static 变量，则该变量不能被修改，抛出 ReflectException

    public String getFinalName() {
        return finalName;
    }
}
