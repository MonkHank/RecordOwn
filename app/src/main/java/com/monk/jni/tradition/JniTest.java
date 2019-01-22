package com.monk.jni.tradition;

/**
 * JNI：java本地接口，方便java调用C、C++等本地代码所封装的一层接口
 * NDK：是Android提供的一个工具集合，通过NDK可以更方便通过JNI来访问本地代码
 *
 * F:\study\AIDLDemo\app\src\main\java\com\monk\jni>
 *      javac -encoding UTF-8 JniTest.java
 *      javah -classpath F:\study\AIDLDemo\app\src\main\java -jni com.monk.jni.JniTest
 *
 * 通过上面命令生成 .h 头文件
 * 还需要通过 gcc 命令生成 .so库，从而完成 java调用jni.
 *
 *
 * @author monk
 * @date 2019-01-22
 */
public class JniTest {

    static{
        System.loadLibrary("jni-test");
    }

    public static void main(String[] args) {
        JniTest jniTest=new JniTest();
        System.out.println(jniTest.get());
        jniTest.set("hello jni");
    }

    public native String get();

    public native void set(String name);
}
