package com.monk.java;

import java.util.Collections;
import java.util.List;

/**
 * @author monk
 * @date 2019-01-08
 */
public class Temporary {
    public static void main(String[] args) {
        String s = OneClass.get();
        System.out.println(s);
    }
}


class OneClass{
    private static List<String> arrayList= Collections.singletonList("OneClass");

    public static String get() {
        return arrayList.get(0);
    }
}
