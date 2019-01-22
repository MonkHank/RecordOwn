//
// Created by Administrator on 2019-01-22.
//
#include "com_monk_jni_JniFragment.h"
#include <stdio.h>

JNIEXPORT jstring JNICALL Java_com_monk_jni_JniFragment_sayHello
   (JNIEnv * env, jobject thiz, jstring string){
       printf("invoke set from c++\n");
       char * str=(char *)env->GetStringUTFChars(string,NULL);
       printf("%s\n",str);
       return env->NewStringUTF(str);
 }

