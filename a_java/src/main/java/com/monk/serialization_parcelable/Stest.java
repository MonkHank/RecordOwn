package com.monk.serialization_parcelable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Stest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Client c = new Client();
        c.id=00001;
        c.name="client";
        c.age = 24;

//         把对象序列化到文件
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream("./javatest/cache"));
        oo.writeObject(c);
        oo.close();

        // 反序列化到内存
        ObjectInputStream oi = new ObjectInputStream(new FileInputStream("./javatest/cache"));
        Object o = oi.readObject();
        oi.close();
        System.out.println(o.toString());

    }
}
