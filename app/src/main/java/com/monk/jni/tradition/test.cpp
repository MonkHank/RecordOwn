//
// Created by Administrator on 2019-01-22.
//
#include "com_monk_jni_JniTest.h"
#include <stdio.h>

JNIEXPORT jstring JNICALL Java_com_monk_jni_JniTest_get
  (JNIEnv * env, jobject thiz){
    printf("invoke get in c++\n");
    return env->NewStringUTF("Hello from JNI !");
  }

JNIEXPORT void JNICALL Java_com_monk_jni_JniTest_set
   (JNIEnv * env, jobject thiz, jstring string){
       printf("invoke set from c++\n");
       char * str=(char *)env->GetStringUTFChars(string,NULL);
       printf("%s\n",str);
       env->ReleaseStringUTFChars(string,str);
 }

