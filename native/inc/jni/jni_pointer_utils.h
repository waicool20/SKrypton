#ifndef SKRYPTONNATIVE_JNI_POINTER_UTILS_H
#define SKRYPTONNATIVE_JNI_POINTER_UTILS_H

#include <jni.h>

template<typename T>
optional<T*> PointerFromCPointer(JNIEnv* env, jobject obj) {
    auto CPointer = GetStaticObjectFieldValue(env, obj, "handle", "com.waicool20.skrypton.jni.CPointe");
    if (CPointer) {
        return { (T*) GetFieldValue<jlong>(env, CPointer.value(), "handle") };
    } else return {};
}

template<typename T>
T DeleteCPointerReference(JNIEnv* env, jobject obj) {
    delete PointerFromCPointer<T>(env, obj);
}

#endif //SKRYPTONNATIVE_JNI_POINTER_UTILS_H
