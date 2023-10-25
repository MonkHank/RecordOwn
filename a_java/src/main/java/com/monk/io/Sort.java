package com.monk.io;

import java.util.Arrays;

/**
 * @author monk
 * @date 2018-12-27
 */
public class Sort {

    public static void main(String[] args) {
        int[] a = {1, 3, 9, 4, 2};
        System.out.println(Arrays.toString(bubbleSort(a)));

        test();
    }


    /**
     * 外层控制趟数
     * 内层控制每趟比较的次数
     * @param a
     * @return
     */
    static  int [] bubbleSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            boolean change=false;
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp=a[j];
                    a[j] = a[j + 1];
                    a[j+1]=temp;
                    change =true;
                }
            }
            if (!change) {
                break;
            }
        }
        return a;
    }

    static int[] selectSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int minIndex =i;
            for (int j = i; j < a.length; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex=j;
                }
            }
            int temp = a[minIndex];
            a[minIndex] = a[i];
            a[i]=temp;
        }
        return a;
    }

    static void test() {
        String highlightStr="null";
        String wholeStr="所属部门："+highlightStr;

        String[] split = wholeStr.split(highlightStr);
        for (String s : split) {
            System.out.println(s);
        }
    }
}
