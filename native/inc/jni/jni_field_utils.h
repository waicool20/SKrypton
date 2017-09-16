#ifndef SKRYPTONNATIVE_JNI_UTIL_H
#define SKRYPTONNATIVE_JNI_UTIL_H

#include <headers.h>
#include <jni.h>

namespace PrivateFieldUtils {
    bool HandleFieldAccessException(JNIEnv* env, string& fieldName);
    bool HandleStaticFieldAccessException(JNIEnv* env, string& fieldName);
}

optional<jfieldID> GetFieldID(JNIEnv* env, jobject obj, string fieldName, string type);
optional<jfieldID> GetStaticFieldID(JNIEnv* env, jobject obj, string fieldName, string type);
optional<jfieldID> GetStaticFieldID(JNIEnv* env, jclass clazz, string fieldName, string type);

//<editor-fold desc="GetFieldValue helper template functions">

template<typename T>
inline optional<T> GetFieldValue(JNIEnv* env, jobject obj, string fieldName) = delete;

template<>
inline optional<jboolean> GetFieldValue<jboolean>(JNIEnv* env, jobject obj, string fieldName) {
    auto fieldID = GetFieldID(env, obj, fieldName, "Z");
    if (!fieldID) return {};
    auto value = env->GetBooleanField(obj, fieldID.value());
    if (PrivateFieldUtils::HandleFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jbyte> GetFieldValue<jbyte>(JNIEnv* env, jobject obj, string fieldName) {
    auto fieldID = GetFieldID(env, obj, fieldName, "B");
    if (!fieldID) return {};
    auto value = env->GetByteField(obj, fieldID.value());
    if (PrivateFieldUtils::HandleFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jchar> GetFieldValue<jchar>(JNIEnv* env, jobject obj, string fieldName) {
    auto fieldID = GetFieldID(env, obj, fieldName, "C");
    if (!fieldID) return {};
    auto value = env->GetCharField(obj, fieldID.value());
    if (PrivateFieldUtils::HandleFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jshort> GetFieldValue<jshort>(JNIEnv* env, jobject obj, string fieldName) {
    auto fieldID = GetFieldID(env, obj, fieldName, "S");
    if (!fieldID) return {};
    auto value = env->GetShortField(obj, fieldID.value());
    if (PrivateFieldUtils::HandleFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jint> GetFieldValue<jint>(JNIEnv* env, jobject obj, string fieldName) {
    auto fieldID = GetFieldID(env, obj, fieldName, "I");
    if (!fieldID) return {};
    auto value = env->GetIntField(obj, fieldID.value());
    if (PrivateFieldUtils::HandleFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jlong> GetFieldValue<jlong>(JNIEnv* env, jobject obj, string fieldName) {
    auto fieldID = GetFieldID(env, obj, fieldName, "J");
    if (!fieldID) return {};
    auto value = env->GetLongField(obj, fieldID.value());
    if (PrivateFieldUtils::HandleFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jfloat> GetFieldValue<jfloat>(JNIEnv* env, jobject obj, string fieldName) {
    auto fieldID = GetFieldID(env, obj, fieldName, "F");
    if (!fieldID) return {};
    auto value = env->GetFloatField(obj, fieldID.value());
    if (PrivateFieldUtils::HandleFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jdouble> GetFieldValue<jdouble>(JNIEnv* env, jobject obj, string fieldName) {
    auto fieldID = GetFieldID(env, obj, fieldName, "D");
    if (!fieldID) return {};
    auto value = env->GetDoubleField(obj, fieldID.value());
    if (PrivateFieldUtils::HandleFieldAccessException(env, fieldName)) return {};
    return { value };
}

//</editor-fold>

//<editor-fold desc="GetStaticFieldValue helper template functions">

template<typename T>
optional<T> GetStaticFieldValue(JNIEnv* env, jobject obj, string fieldName) = delete;

template<>
inline optional<jboolean> GetStaticFieldValue<jboolean>(JNIEnv* env, jobject obj, string fieldName) {
    jclass clazz = env->GetObjectClass(obj);
    auto sFieldID = GetStaticFieldID(env, clazz, fieldName, "Z");
    if (sFieldID) return {};
    auto value = env->GetStaticBooleanField(clazz, sFieldID.value());
    if (PrivateFieldUtils::HandleStaticFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jbyte> GetStaticFieldValue<jbyte>(JNIEnv* env, jobject obj, string fieldName) {
    jclass clazz = env->GetObjectClass(obj);
    auto sFieldID = GetStaticFieldID(env, clazz, fieldName, "B");
    if (sFieldID) return {};
    auto value = env->GetStaticByteField(clazz, sFieldID.value());
    if (PrivateFieldUtils::HandleStaticFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jchar> GetStaticFieldValue<jchar>(JNIEnv* env, jobject obj, string fieldName) {
    jclass clazz = env->GetObjectClass(obj);
    auto sFieldID = GetStaticFieldID(env, clazz, fieldName, "C");
    if (sFieldID) return {};
    auto value = env->GetStaticCharField(clazz, sFieldID.value());
    if (PrivateFieldUtils::HandleStaticFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jshort> GetStaticFieldValue<jshort>(JNIEnv* env, jobject obj, string fieldName) {
    jclass clazz = env->GetObjectClass(obj);
    auto sFieldID = GetStaticFieldID(env, clazz, fieldName, "S");
    if (sFieldID) return {};
    auto value = env->GetStaticShortField(clazz, sFieldID.value());
    if (PrivateFieldUtils::HandleStaticFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jint> GetStaticFieldValue<jint>(JNIEnv* env, jobject obj, string fieldName) {
    jclass clazz = env->GetObjectClass(obj);
    auto sFieldID = GetStaticFieldID(env, clazz, fieldName, "I");
    if (sFieldID) return {};
    auto value = env->GetStaticIntField(clazz, sFieldID.value());
    if (PrivateFieldUtils::HandleStaticFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jlong> GetStaticFieldValue<jlong>(JNIEnv* env, jobject obj, string fieldName) {
    jclass clazz = env->GetObjectClass(obj);
    auto sFieldID = GetStaticFieldID(env, clazz, fieldName, "J");
    if (sFieldID) return {};
    auto value = env->GetStaticLongField(clazz, sFieldID.value());
    if (PrivateFieldUtils::HandleStaticFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jfloat> GetStaticFieldValue<jfloat>(JNIEnv* env, jobject obj, string fieldName) {
    jclass clazz = env->GetObjectClass(obj);
    auto sFieldID = GetStaticFieldID(env, clazz, fieldName, "F");
    if (sFieldID) return {};
    auto value = env->GetStaticFloatField(clazz, sFieldID.value());
    if (PrivateFieldUtils::HandleStaticFieldAccessException(env, fieldName)) return {};
    return { value };
}

template<>
inline optional<jdouble> GetStaticFieldValue<jdouble>(JNIEnv* env, jobject obj, string fieldName) {
    jclass clazz = env->GetObjectClass(obj);
    auto sFieldID = GetStaticFieldID(env, clazz, fieldName, "D");
    if (sFieldID) return {};
    auto value = env->GetStaticDoubleField(clazz, sFieldID.value());
    if (PrivateFieldUtils::HandleStaticFieldAccessException(env, fieldName)) return {};
    return { value };
}

//</editor-fold>

//<editor-fold desc="Get(Static)ObjectFieldValue helper functions">

optional<jobject> GetObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type);
optional<jobject> GetStaticObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type);

//</editor-fold>

#endif //SKRYPTONNATIVE_JNI_UTIL_H
