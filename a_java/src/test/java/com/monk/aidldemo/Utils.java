package com.monk.aidldemo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author monk
 * @date 2019-04-16
 */
public class Utils {

    public static List<String> strList() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");

        return list;
    }
}
class Generic<T>{
    //key这个成员变量的类型为T,T的类型由外部指定
    private T key;

    public Generic() {
    }

    public Generic(T key) { //泛型构造方法形参key的类型也为T，T的类型由外部指定
        this.key = key;
        Number number=new Number() {
            @Override
            public int intValue() {
                return 0;
            }

            @Override
            public long longValue() {
                return 0;
            }

            @Override
            public float floatValue() {
                return 0;
            }

            @Override
            public double doubleValue() {
                return 0;
            }
        };

    }

    public T getKey(){ //泛型方法getKey的返回值类型为T，T的类型由外部指定
        return key;
    }
}

class MyGeneric<T extends MyGeneric> extends Generic{
    private final Generic member;
    public MyGeneric() {
        member= this;
    }
}