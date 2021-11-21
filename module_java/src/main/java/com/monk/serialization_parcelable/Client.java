package com.monk.serialization_parcelable;

import java.io.Serializable;

public class Client implements Serializable {

    private static final long serialVersionUID = -5993325517374144399L;

    int id;
    String name;

    transient int age; // 使用transient关键字标记的成员变量也不参与序列化过程


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


}
