#ifndef SKRYPTONNATIVE_JNI_METHOD_UTILS_H
#define SKRYPTONNATIVE_JNI_METHOD_UTILS_H

#include <headers.h>
#include <jni.h>

template<typename T>
inline optional<T> CallMethod(JNIEnv* env) = delete;

template<>
inline optional<void> CallMethod<void>(JNIEnv* env) {
    //env->CallVoidMethod()
}

#endif //SKRYPTONNATIVE_JNI_METHOD_UTILS_H
