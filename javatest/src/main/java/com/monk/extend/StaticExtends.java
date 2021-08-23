package com.monk.extend;

public class StaticExtends {
    public static void main(String[] args) {
        One one = new Two();
        one.oneFn();
        System.out.println(one.one_1);
    }
}

class One {
    static String one_1="one";
    static void oneFn(){
        System.out.println("oneFn");
    }
}

class Two extends One {
    static String one_1="two";
    static void oneFn(){
        System.out.println("twoFn");
    }



}


