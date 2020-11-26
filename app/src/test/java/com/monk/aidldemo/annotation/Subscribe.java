package com.monk.aidldemo.annotation;

import com.google.android.material.snackbar.BaseTransientBottomBar;

import org.junit.runner.RunWith;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Subscribe {
    int priority() default 0;

    boolean sticky() default false;

}
