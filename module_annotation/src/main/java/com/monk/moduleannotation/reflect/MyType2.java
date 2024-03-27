package com.monk.moduleannotation.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author monk
 * @date 2024/3/8 下午 5:15 周五
 */
public class MyType2<T> {
  final Type type;

  MyType2() {
    Type superclass = getClass().getGenericSuperclass();
    if (superclass instanceof Class) {
      throw new RuntimeException("Missing type parameter.");
    }
    ParameterizedType parameterized = (ParameterizedType) superclass;
    type = parameterized.getActualTypeArguments()[0];
    System.out.println("============================================");
    System.out.println("============================================");
    System.out.println("============================================");
    System.out.println("type = "+type);
  }

  public static void main(String[] args) {
    new MyType2<Map<String,Integer>>(){};
  }
}
