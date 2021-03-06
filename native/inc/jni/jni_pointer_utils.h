#ifndef SKRYPTONNATIVE_JNI_POINTER_UTILS_H
#define SKRYPTONNATIVE_JNI_POINTER_UTILS_H

#include <jni.h>

template<typename T>
optional<T*> PointerFromCPointer(JNIEnv* env, jobject obj) {
    auto CPointer = GetObjectFieldValue(env, obj, "handle", "com.waicool20.skrypton.jni.CPointer");
    if (CPointer) {
        auto longPtr = GetFieldValue<jlong>(env, CPointer.value(), "value");
        if (longPtr) return { (T*) longPtr.value() };
    }
    return {};
}

template<typename T>
optional<T*> PointerFromStaticCPointer(JNIEnv* env, jobject obj) {
    auto CPointer = GetStaticObjectFieldValue(env, obj, "value", "com.waicool20.skrypton.jni.CPointer");
    if (CPointer) {
        auto longPtr = GetFieldValue<jlong>(env, CPointer.value(), "value");
        if (longPtr) return { (T*) longPtr.value() };
    }
    return {};
}

template<typename T>
T DeleteCPointerReference(JNIEnv* env, jobject obj) {
    delete PointerFromCPointer<T>(env, obj);
}

template<typename T>
T DeleteStaticCPointerReference(JNIEnv* env, jobject obj) {
    delete PointerFromStaticCPointer<T>(env, obj);
}

#endif //SKRYPTONNATIVE_JNI_POINTER_UTILS_H
