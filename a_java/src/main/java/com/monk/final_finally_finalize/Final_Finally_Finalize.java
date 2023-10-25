package com.monk.final_finally_finalize;

public class Final_Finally_Finalize {

    public static int test(){
        int i =1;
//        if (i==1)return 0;

        System.out.println("第一句语句block");
        i=i/0;
        try {
            System.out.println("try 语句");
            return i;
        }
        finally {
            System.out.println("finally 语句");
        }
    }

    public static void main(String[] args) {

//        test();

        FinalClass fc = new FinalClass();
        fc.memberVariable="c";
        fc.memberVariable="d";
        System.out.println(fc.memberVariable);
    }
}
