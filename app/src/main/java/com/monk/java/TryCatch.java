package com.monk.java;

/**
 * @author monk
 * @date 2019-01-16
 */
public class TryCatch {
    static int calculate0(int a,int b) throws Exception{
        return a/b;
    }

    static int calculate1(int a,int b) {

        try {
            return calculate0(1,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 函数内部try catch，调用者在try catch是没用的
     * @param args
     */
    public static void main(String[] args) {
        int calculate = 0;
        try {
            calculate = calculate1(1, 0);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        System.out.println(Integer.parseInt("8080"));


        System.out.println(calculate);
        System.out.println("执行了");

        System.out.println(isIp("228.110.0.1"));
        System.out.println(isIp("196.136.69.6"));
    }

    static boolean isIp(String ip) {
       String rext = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."

               +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."

               +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."

               +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

       return ip.matches(rext);
    }
}
