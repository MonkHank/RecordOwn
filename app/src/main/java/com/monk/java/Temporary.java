package com.monk.java;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.net.URL;
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

        ClassLoader classLoader = OneClass.class.getClassLoader();
        URL resource = classLoader.getResource("");

    }
}


class OneClass{
    private static final List<String> arrayList= Collections.singletonList("OneClass");

    public static String get() {
        return arrayList.get(0);
    }
}

class Activity{
    public void onCreate() {

        LooperThread thread = new LooperThread();
        thread.start();
        thread.looperHandler.sendMessage(Message.obtain());
    }
}

class LooperThread extends Thread{

    Handler looperHandler ;
    @Override
    public void run() {
        Looper.prepare();
        looperHandler= new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg) {

            }
        };
        Looper.loop();
    }
}

