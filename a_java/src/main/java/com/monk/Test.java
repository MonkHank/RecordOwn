package com.monk;

public class Test {

  public static void main(String[] args) {
    int mGroupFlags = 1;
    int FLAG_DISALLOW_INTERCEPT = 0x80000;

//        mGroupFlags |= FLAG_DISALLOW_INTERCEPT; // !=0
//        mGroupFlags &= ~FLAG_DISALLOW_INTERCEPT; // =0

    int result = mGroupFlags & FLAG_DISALLOW_INTERCEPT;

    System.out.println("result = " + result);

  }
}
