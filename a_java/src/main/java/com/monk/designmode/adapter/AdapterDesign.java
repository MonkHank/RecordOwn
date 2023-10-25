package com.monk.designmode.adapter;

/**
 * Java 中的单继承，多实现，extends 都是放在前面，implements 后面，也就是类的多实现，即java的多继承
 * <p>
 * 典型的类适配器模式
 *
 * @author monk
 * @date 2019-05-31
 */
public class AdapterDesign extends Phone implements Usb {

    @Override
    public void store() {

    }
}


//  典型的类适配器代码
//public class Adapter extends Adaptee implements Target {
//
//    public void request() {
//        specificRequest();
//    }
//
//}

//  典型的对象适配器代码
//public class Adapter extends Target {
//    private Adaptee adaptee;
//
//    public Adapter(Adaptee adaptee) {
//        this.adaptee = adptee;
//    }
//
//    public void request() {
//        adaptee.specificRequest();
//    }
//}
