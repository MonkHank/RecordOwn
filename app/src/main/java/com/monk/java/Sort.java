package com.monk.java;

/**
 * @author monk
 * @date 2018-12-27
 */
public class Sort {


    public int [] bubbleSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp=a[j];
                    a[j] = a[j + 1];
                    a[j+1]=temp;
                }
            }
        }
        return a;
    }

    public int[] selectSort(int a[]) {
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
}
