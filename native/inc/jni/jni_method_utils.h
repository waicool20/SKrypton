#ifndef SKRYPTONNATIVE_JNI_METHOD_UTILS_H
#define SKRYPTONNATIVE_JNI_METHOD_UTILS_H

#include <jni_utils.h>

namespace PrivateMethodUtils {
    bool HandleMethodIDException(JNIEnv* env, jobject obj, const string& methodName, const string& signature);
}

//<editor-fold desc="CallMethod helper template functions">

template<typename T>
inline optional<T> CallMethod(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) = delete;

template<>
inline optional<void*>
CallMethod<void*>(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, obj, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    env->CallVoidMethodV(obj, methodID, args);
    return {};
}

template<>
inline optional<jobject>
CallMethod<jobject>(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, obj, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallObjectMethodV(obj, methodID, args) };
};

template<>
inline optional<jboolean>
CallMethod<jboolean>(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, obj, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallBooleanMethodV(obj, methodID, args) };
};

template<>
inline optional<jbyte>
CallMethod<jbyte>(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, obj, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallByteMethodV(obj, methodID, args) };
};

template<>
inline optional<jchar>
CallMethod<jchar>(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, obj, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallCharMethodV(obj, methodID, args) };
};

template<>
inline optional<jshort>
CallMethod<jshort>(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, obj, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallShortMethodV(obj, methodID, args) };
};

template<>
inline optional<jint>
CallMethod<jint>(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, obj, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallIntMethodV(obj, methodID, args) };
};

template<>
inline optional<jlong>
CallMethod<jlong>(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, obj, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallLongMethodV(obj, methodID, args) };
};

template<>
inline optional<jfloat>
CallMethod<jfloat>(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, obj, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallFloatMethodV(obj, methodID, args) };
};

template<>
inline optional<jdouble>
CallMethod<jdouble>(JNIEnv* env, jobject obj, const string& methodName, const string& signature, ...) {
    auto clazz = env->GetObjectClass(obj);
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, obj, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallDoubleMethodV(obj, methodID, args) };
};

//</editor-fold>

//<editor-fold desc="CallStaticMethod helper template functions">

template<typename T, class... Args>
inline optional<T> CallStaticMethod(JNIEnv* env, string type, const string& methodName, const string& signature, Args&&... args) {
    replace(type.begin(), type.end(), '.', '/');
    return CallStaticMethod<T>(env, env->FindClass(type.c_str()), methodName, signature, forward<Args>(args)...);
}

template<typename T>
inline optional<T> CallStaticMethod(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) = delete;

template<>
inline optional<void*> CallStaticMethod<void*>(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) {
    auto methodID = env->GetMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    env->CallStaticVoidMethodV(clazz, methodID, args);
    return {};
};

template<>
inline optional<jobject>
CallStaticMethod<jobject>(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallStaticObjectMethodV(clazz, methodID, args) };
};

template<>
inline optional<jboolean>
CallStaticMethod<jboolean>(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallStaticBooleanMethodV(clazz, methodID, args) };
};

template<>
inline optional<jbyte>
CallStaticMethod<jbyte>(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallStaticByteMethodV(clazz, methodID, args) };
};

template<>
inline optional<jchar>
CallStaticMethod<jchar>(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallStaticCharMethodV(clazz, methodID, args) };
};

template<>
inline optional<jshort>
CallStaticMethod<jshort>(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallStaticShortMethodV(clazz, methodID, args) };
};

template<>
inline optional<jint>
CallStaticMethod<jint>(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallStaticIntMethodV(clazz, methodID, args) };
};

template<>
inline optional<jlong>
CallStaticMethod<jlong>(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallStaticLongMethodV(clazz, methodID, args) };
};

template<>
inline optional<jfloat>
CallStaticMethod<jfloat>(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallStaticFloatMethodV(clazz, methodID, args) };
};

template<>
inline optional<jdouble>
CallStaticMethod<jdouble>(JNIEnv* env, jclass clazz, const string& methodName, const string& signature, ...) {
    auto methodID = env->GetStaticMethodID(clazz, methodName.c_str(), signature.c_str());
    if (PrivateMethodUtils::HandleMethodIDException(env, clazz, methodName, signature)) return {};

    va_list args;
    va_start(args, signature);
    return { env->CallStaticDoubleMethodV(clazz, methodID, args) };
};

//</editor-fold>

#endif //SKRYPTONNATIVE_JNI_METHOD_UTILS_H
