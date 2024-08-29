package com.monk.moduleannotation.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author monk
 * @date 2024/3/8 下午 5:15 周五
 */
public class MyType extends HashMap<String,String> {

  final Type type;

  MyType() {
    Type superclass = getClass().getGenericSuperclass();
    if (superclass instanceof Class) {
      throw new RuntimeException("Missing type parameter.");
    }
    ParameterizedType parameterized = (ParameterizedType) superclass;
    type = parameterized.getActualTypeArguments()[0];
    System.out.println("superclass = "+superclass);
    System.out.println("type = "+type);
  }

  public static void main(String[] args) {
    new MyType();
  }
}
