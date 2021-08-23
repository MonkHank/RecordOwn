package com.monk.javatest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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


        String s = new String("a");
        System.out.println(s);

//        httpBaidu();

        String str1="abc";
        String str2="def";
        String str3=str1+str2;
        System.out.println("===========test5============");
        System.out.println(str3=="abcdef"); //false
    }

    private static void test6(){
        String s0 ="a1";
        String s1="a"+1;

    }

    private static void httpBaidu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream in = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
