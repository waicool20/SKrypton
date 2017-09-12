#ifndef SKRYPTONNATIVE_JNI_UTIL_H
#define SKRYPTONNATIVE_JNI_UTIL_H

#include <headers.h>
#include <jni.h>

void CheckExceptions(JNIEnv* env);
void CheckObjNull(JNIEnv* env, const jobject &obj);
jfieldID GetFieldID(JNIEnv* env, jobject obj, string fieldName, string type);

//<editor-fold desc="GetFieldValue helper template functions">

template<typename T>
inline T GetFieldValue(JNIEnv* env, jobject obj, string fieldName) = delete;

template<>
inline jboolean GetFieldValue<jboolean>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    return env->GetBooleanField(obj, GetFieldID(env, obj, fieldName, "Z"));
}

template<>
inline jbyte GetFieldValue<jbyte>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    return env->GetByteField(obj, GetFieldID(env, obj, fieldName.c_str(), "B"));
}

template<>
inline jchar GetFieldValue<jchar>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    return env->GetCharField(obj, GetFieldID(env, obj, fieldName.c_str(), "C"));
}

template<>
inline jshort GetFieldValue<jshort>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    return env->GetShortField(obj, GetFieldID(env, obj, fieldName.c_str(), "S"));
}

template<>
inline jint GetFieldValue<jint>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    return env->GetIntField(obj, GetFieldID(env, obj, fieldName.c_str(), "I"));
}

template<>
inline jlong GetFieldValue<jlong>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    return env->GetLongField(obj, GetFieldID(env, obj, fieldName.c_str(), "J"));
}

template<>
inline jfloat GetFieldValue<jfloat>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    return env->GetFloatField(obj, GetFieldID(env, obj, fieldName.c_str(), "F"));
}

template<>
inline jdouble GetFieldValue<jdouble>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    return env->GetDoubleField(obj, GetFieldID(env, obj, fieldName.c_str(), "D"));
}

//</editor-fold>

//<editor-fold desc="GetStaticFieldValue helper template functions">

template<typename T>
T GetStaticFieldValue(JNIEnv* env, jobject obj, string fieldName) = delete;

template<>
inline jboolean GetStaticFieldValue<jboolean>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticBooleanField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "Z"));
}

template<>
inline jbyte GetStaticFieldValue<jbyte>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticByteField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "B"));
}

template<>
inline jchar GetStaticFieldValue<jchar>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticCharField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "C"));
}

template<>
inline jshort GetStaticFieldValue<jshort>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticShortField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "S"));
}

template<>
inline jint GetStaticFieldValue<jint>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticIntField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "I"));
}

template<>
inline jlong GetStaticFieldValue<jlong>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticLongField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "J"));
}

template<>
inline jfloat GetStaticFieldValue<jfloat>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticFloatField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "F"));
}

template<>
inline jdouble GetStaticFieldValue<jdouble>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticDoubleField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "D"));
}

//</editor-fold>

//<editor-fold desc="Get(Static)ObjectFieldValue helper functions">

jobject GetObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type);
jobject GetStaticObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type);

//</editor-fold>

#endif //SKRYPTONNATIVE_JNI_UTIL_H
