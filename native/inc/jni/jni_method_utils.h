#ifndef SKRYPTONNATIVE_JNI_METHOD_UTILS_H
#define SKRYPTONNATIVE_JNI_METHOD_UTILS_H

#include <jni_utils.h>

namespace PrivateMethodUtils {
    bool HandleMethodIDException(JNIEnv* env, jobject obj, string& methodName, string& signature);
}

//<editor-fold desc="CallMethod helper template functions">

template<typename T>
inline optional<T> CallMethod(JNIEnv* env, jobject obj, string methodName, string signature, ...) = delete;

template<>
inline optional<jobject>
CallMethod<jobject>(JNIEnv* env, jobject obj, string methodName, string signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallObjectMethodV(clazz, methodID, args) };
};

template<>
inline optional<jboolean>
CallMethod<jboolean>(JNIEnv* env, jobject obj, string methodName, string signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallBooleanMethodV(clazz, methodID, args) };
};

template<>
inline optional<jbyte>
CallMethod<jbyte>(JNIEnv* env, jobject obj, string methodName, string signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallByteMethodV(clazz, methodID, args) };
};

template<>
inline optional<jchar>
CallMethod<jchar>(JNIEnv* env, jobject obj, string methodName, string signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallCharMethodV(clazz, methodID, args) };
};

template<>
inline optional<jshort>
CallMethod<jshort>(JNIEnv* env, jobject obj, string methodName, string signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallShortMethodV(clazz, methodID, args) };
};

template<>
inline optional<jint>
CallMethod<jint>(JNIEnv* env, jobject obj, string methodName, string signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallIntMethodV(clazz, methodID, args) };
};

template<>
inline optional<jlong>
CallMethod<jlong>(JNIEnv* env, jobject obj, string methodName, string signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallLongMethodV(clazz, methodID, args) };
};

template<>
inline optional<jfloat>
CallMethod<jfloat>(JNIEnv* env, jobject obj, string methodName, string signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallFloatMethodV(clazz, methodID, args) };
};

template<>
inline optional<jdouble>
CallMethod<jdouble>(JNIEnv* env, jobject obj, string methodName, string signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallDoubleMethodV(clazz, methodID, args) };
};

//</editor-fold>

//<editor-fold desc="CallStaticMethod helper template functions">

template<typename T, class... Args>
inline optional<T> CallStaticMethod(JNIEnv* env, string type, string methodName, string signature, Args&&... args) {
    replace(type.begin(), type.end(), '.', '/');
    return CallStaticMethod<T>(env, env->FindClass(type.c_str()), methodName, signature, forward<Args>(args)...);
}

template<typename T>
inline optional<T> CallStaticMethod(JNIEnv* env, jclass clazz, string methodName, string signature, ...) = delete;

template<>
inline optional<void*> CallStaticMethod<void*>(JNIEnv* env, jclass clazz, string methodName, string signature, ...) {
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    env->CallStaticVoidMethodV(clazz, methodID, args);
    return {};
};

template<>
inline optional<jobject>
CallStaticMethod<jobject>(JNIEnv* env, jclass clazz, string methodName, string signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallStaticObjectMethodV(clazz, methodID, args) };
};

template<>
inline optional<jboolean>
CallStaticMethod<jboolean>(JNIEnv* env, jclass clazz, string methodName, string signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallStaticBooleanMethodV(clazz, methodID, args) };
};

template<>
inline optional<jbyte>
CallStaticMethod<jbyte>(JNIEnv* env, jclass clazz, string methodName, string signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallStaticByteMethodV(clazz, methodID, args) };
};

template<>
inline optional<jchar>
CallStaticMethod<jchar>(JNIEnv* env, jclass clazz, string methodName, string signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallStaticCharMethodV(clazz, methodID, args) };
};

template<>
inline optional<jshort>
CallStaticMethod<jshort>(JNIEnv* env, jclass clazz, string methodName, string signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallStaticShortMethodV(clazz, methodID, args) };
};

template<>
inline optional<jint>
CallStaticMethod<jint>(JNIEnv* env, jclass clazz, string methodName, string signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallStaticIntMethodV(clazz, methodID, args) };
};

template<>
inline optional<jlong>
CallStaticMethod<jlong>(JNIEnv* env, jclass clazz, string methodName, string signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallStaticLongMethodV(clazz, methodID, args) };
};

template<>
inline optional<jfloat>
CallStaticMethod<jfloat>(JNIEnv* env, jclass clazz, string methodName, string signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallStaticFloatMethodV(clazz, methodID, args) };
};

template<>
inline optional<jdouble>
CallStaticMethod<jdouble>(JNIEnv* env, jclass clazz, string methodName, string signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    return { env->CallStaticDoubleMethodV(clazz, methodID, args) };
};

//</editor-fold>

#endif //SKRYPTONNATIVE_JNI_METHOD_UTILS_H
