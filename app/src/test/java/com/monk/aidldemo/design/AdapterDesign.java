package com.monk.aidldemo.design;

/**
 * Java 中的单继承，多实现，extends 都是放在前面，implements 后面，也就是类的多实现，即java的多继承
 *
 * 典型的类适配器模式
 * @author monk
 * @date 2019-05-31
 */
public class AdapterDesign extends Phone implements Usb{

    @Override
    public void store() {

    }
}


