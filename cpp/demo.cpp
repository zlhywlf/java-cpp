/**
 * @author zlhywlf
 */
#ifndef DEMO_H
#define DEMO_H

#include <iostream>
#include "jni.h"

#ifdef __cplusplus
extern "C"
{
#endif

JNIEXPORT jstring JNICALL Java_zlhywlf_demo_DemoImpl_helloWorld(JNIEnv *env, jobject obj, jstring str){
    std::cout<<"cpp: " << env->GetStringUTFChars(str,JNI_FALSE) << std::endl;
    return env->NewStringUTF("hello world!");
}

#ifdef __cplusplus
}
#endif

#endif // DEMO_H