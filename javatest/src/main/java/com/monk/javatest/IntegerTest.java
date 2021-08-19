package com.monk.javatest;

public class IntegerTest {

    public static void main(String[] args) {
        Integer i = 128;
        Integer j = 128;
        System.out.println(i==j);
        System.out.println(i.equals(j));

        Integer k = -126;
        Integer l = -126;
        System.out.println(k==l);
        System.out.println(k.equals(l));
    }
}
