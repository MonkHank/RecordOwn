package com.monk.aidldemo.transienttest;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author monk
 * @date 2019/7/1
 * https://www.cnblogs.com/chenpi/p/6185773.html
 */
public class Rectangle implements Serializable {
    private static final long serialVersionUID = -4998544199541672116L;

    private int width, height;
    private transient Integer area;

    public Rectangle() {
    }


    public void setArea() {
        this.area = this.width * this.height;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(40);
        sb.append("width : ").append(this.width).append("\t");
        sb.append("height : ").append(this.height).append("\t");
        sb.append("area : ").append(this.area).append("\t");
        return sb.toString();
    }

    @Test
    public void testmain() throws Exception {
        Rectangle rectangle = new Rectangle();
        rectangle.width=3;
        rectangle.height=4;
        rectangle.setArea();
        System.out.println("1.原始对象\t\t" + rectangle);

        //  RecordOwn/app/rectangle
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("rectangle"));
        o.writeObject(rectangle); // 往流写入对象
        o.close();

        // 从流读取对象
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("rectangle"));
        Rectangle rectangle1 = (Rectangle) in.readObject();
        in.close();
        System.out.println("2.反序列化后的对象\t" + rectangle1);

        rectangle1.setArea();
        System.out.println("3.恢复成原始对象\t" + rectangle1);
    }
}
