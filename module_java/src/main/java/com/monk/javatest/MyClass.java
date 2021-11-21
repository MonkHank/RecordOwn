package com.monk.javatest;

public class MyClass {

    public static void testPwd() {


        /**
         *  ^ 匹配一行的开头位置
         *  (?![0-9]+$) 预测该位置后面不全是数字
         *  (?![a-zA-Z]+$) 预测该位置后面不全是字母
         *  [0-9A-Za-z] {8,16} 由8-16位数字或这字母组成
         *  ~!@#$%^&*_：特殊字符可有可无
         *  $ 匹配行结尾位置
         */
        String regex2 = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z~!@#$%^&*_]{8,16}$";

        System.out.println("aaa1111a".matches(regex2));
    }

    public static void hideMid4(String mobile) {
        System.out.println(mobile.substring(0,3)+"****"+mobile.substring(7));
    }
    public static void formatPhone(String mobile) {
        System.out.println(mobile.substring(0,2)
                +" "+mobile.substring(2,5)
                +" "+mobile.substring(5,9)
                +" "+mobile.substring(9));
    }

}
