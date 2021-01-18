package com.monk.moduleannotation.annotation;

/**
 * @author monk
 * @date 2019/7/9
 */
@Deprecated
public class AnnotationTest {
    public static void main(String[] args) {
        Override annotation = new AnnotationTest().getClass().getAnnotation(Override.class);
        Deprecated deprecated = new AnnotationTest().getClass().getAnnotation(Deprecated.class);


        System.out.println("overider = "+annotation);
        System.out.println("deprecated = "+deprecated);
    }
}
