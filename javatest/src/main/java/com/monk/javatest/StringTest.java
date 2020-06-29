package com.monk.javatest;

import java.util.Locale;

/**
 * @author monk
 * @date 2019/7/18
 */
public class StringTest {


    static  void testStringFormat() {
        String result = String.format("%s?%s", "www.baidu.com",null);
        System.out.println(result);

        String format = String.format(Locale.US, "%.1f%%", 0.86 * 100);
        System.out.println(format);

        String phoneNumber = String.format(Locale.US, "%d%s", 10086,"1515");
        System.out.println(phoneNumber);
    }



    public static void main(String[] args) {
        testStringFormat();

        MyClass.testPwd();

        MyClass.hideMid4("15105100141");

        MyClass.formatPhone("8615105199141");
    }
}
